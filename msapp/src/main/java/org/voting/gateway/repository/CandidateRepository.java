package org.voting.gateway.repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.Candidate;
import org.voting.gateway.domain.SmallUser;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


@Repository
public class CandidateRepository {

    private Mapper<Candidate> mapper;
    private final CassandraSession cassandraSession;


    public CandidateRepository(CassandraSession cassandraSession) {
        this.cassandraSession = cassandraSession;
        mapper = cassandraSession.getMappingManager().mapper(Candidate.class);
    }


    public Candidate save(Candidate candidate)
    {
        mapper.save(candidate);
        return candidate;
    }

    public Page<Candidate> findAllPaged(Pageable pageRequest)
    {
    	Statement statement = new SimpleStatement("SELECT * FROM candidate");        
        statement.setFetchSize(50);
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

        return new PageImpl<Candidate>(candidatesOnPage) ;
    }

    public Candidate findOne(UUID id)
    {
        Candidate res = mapper.get(id);
        return res;
    }

    public void delete(UUID id)
    {
        mapper.delete(id);
    }

    public List<Candidate> findInMunicipality(UUID municipalityId)
    {
        ResultSet results = cassandraSession.getSession().execute("SELECT * FROM candidate WHERE commune = '" + municipalityId + "'");
        Result<Candidate> candidates = mapper.map(results);
        return candidates.all();
    }


    public List<Candidate> findInMunicipalityByTurn(UUID municipalityId, UUID turn) {
        Statement statement = new SimpleStatement("SELECT * FROM candidate " +
            "WHERE municipality_id = ? AND turns CONTAINS ?", municipalityId, turn);

        ResultSet results = cassandraSession.getSession().execute(statement);
        Result<Candidate> candidates = mapper.map(results);
        return candidates.all();
    }
}
