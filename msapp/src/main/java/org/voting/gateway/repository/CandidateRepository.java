package org.voting.gateway.repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.Candidate;
import org.voting.gateway.service.VotesDesignationSingleCandidateDTO;

import java.util.*;
import java.util.stream.Collectors;


@Repository
public class CandidateRepository {

    private Mapper<Candidate> mapper;
    private final CassandraSession cassandraSession;
    private final VotesSumRepository votesSumRepository;

    public CandidateRepository(CassandraSession cassandraSession, VotesSumRepository votesSumRepository) {
        this.cassandraSession = cassandraSession;
        mapper = cassandraSession.getMappingManager().mapper(Candidate.class);
        this.votesSumRepository = votesSumRepository;
    }


    public Candidate save(Candidate candidate)
    {
        mapper.save(candidate);
        return candidate;
    }

    public PageWithTotalCount<Candidate> findAllPaged(Pageable pageRequest)
    {
    	Statement statement = new SimpleStatement("SELECT * FROM candidate");
        statement.setFetchSize(50);
        int total = (int) cassandraSession.getSession().execute(new SimpleStatement("SELECT COUNT(*) FROM candidate"))
            .one() .getLong(0);
        ResultSet results = cassandraSession.getSession().execute(statement);
        Result<Candidate> candidates = mapper.map(results);
        //skip
        for (int i = 0; i < pageRequest.getOffset() &&  !candidates.isExhausted(); i++) {
            candidates.one();
        }
        List<Candidate> candidatesOnPage = new LinkedList<>();
        for (int i = 0; i < pageRequest.getPageSize() &&  !candidates.isExhausted() ; i++) {
        	candidatesOnPage.add(candidates.one());
        }

        return new PageWithTotalCount<Candidate>(total, new PageImpl<Candidate>(candidatesOnPage, pageRequest, total)) ;
    }

    public Candidate findOne(UUID id)
    {
        Candidate res = mapper.get(id);
        return res;
    }

    public void delete(UUID id)
    {
        ResultSet results = cassandraSession.getSession().execute("SELECT votes_id FROM votes_from_ward WHERE " +
            "candidate = ? ALLOW FILTERING", id);

        if(!results.isExhausted()) throw new RuntimeException("Cant delete candidate");
        mapper.delete(id);
    }

    public List<Candidate> findInMunicipality(UUID municipalityId)
    {
        ResultSet results = cassandraSession.getSession().execute("SELECT * FROM candidate WHERE commune = ?",
            municipalityId);
        Result<Candidate> candidates = mapper.map(results);
        return candidates.all();
    }


    public List<Candidate> findInMunicipalityByTurn(UUID municipalityId, int turnNum) {
        if(turnNum == 1){
            ResultSet results = cassandraSession.getSession().execute("SELECT * FROM candidate WHERE commune = ?",
                municipalityId);
            Result<Candidate> candidates = mapper.map(results);
            return candidates.all();
        }else if (turnNum == 2) {
            List<VotesDesignationSingleCandidateDTO> candidateVotes = votesSumRepository
                .getAllVotesInMunicipality(municipalityId, turnNum).getCandidate_votes().stream()
                .sorted(Comparator.comparing(VotesDesignationSingleCandidateDTO::getNumber_of_votes))
                .collect(Collectors.toList());
            if( candidateVotes.size() == 0 ){
                return new ArrayList<>();
            }if( candidateVotes.size() == 1){
                return Collections.singletonList(
                    findOne(candidateVotes.get(0).getCandidate_id())
                );
            }else{
                return Arrays.asList(
                    findOne(candidateVotes.get(0).getCandidate_id()),
                    findOne(candidateVotes.get(1).getCandidate_id())
                );
            }
        }else{
            throw new RuntimeException("E623: "+turnNum);
        }
    }
}
