package org.voting.gateway.repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.utils.UUIDs;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;
import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.BPlanSingleCandidateVote;
import org.voting.gateway.domain.BPlanVotesDesignationPack;
import org.voting.gateway.service.VotesDesignationSingleCandidateDTO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by defacto on 6/8/2018.
 */
@Repository
public class BPlanSingleCandidateVoteRepository {
    private Mapper<BPlanSingleCandidateVote> mapper;
    private final CassandraSession cassandraSession;

    public BPlanSingleCandidateVoteRepository(CassandraSession cassandraSession) {
        this.cassandraSession = cassandraSession;
        this.mapper = cassandraSession.getMappingManager().mapper(BPlanSingleCandidateVote.class);
    }

    public List<VotesDesignationSingleCandidateDTO> findByPackId(UUID pack_id) {
        Statement statement = new SimpleStatement("SELECT * FROM bPlanSingleCandidateVote WHERE pack_id = ? ALLOW  " +
            "FILTERING", pack_id);

        ResultSet results = cassandraSession.getSession().execute(statement);
        Result<BPlanSingleCandidateVote> votes = mapper.map(results);
        return votes.all().stream().map(c -> new VotesDesignationSingleCandidateDTO(c.getCandidate_id(), c.getVotes_count())).collect(Collectors.toList());
    }

    public List<BPlanSingleCandidateVote> findByPackIdNoDto(UUID pack_id) {
        Statement statement = new SimpleStatement("SELECT * FROM bPlanSingleCandidateVote WHERE pack_id = ? ALLOW  " +
            "FILTERING", pack_id);

        ResultSet results = cassandraSession.getSession().execute(statement);
        Result<BPlanSingleCandidateVote> votes = mapper.map(results);
        return votes.all();
    }

    public void save(VotesDesignationSingleCandidateDTO c, UUID packId) {
        BPlanSingleCandidateVote scv = new BPlanSingleCandidateVote();
        scv.setCandidate_id(c.getCandidate_id());
        scv.setPack_id(packId);
        scv.setSingle_candidate_vote_id(UUIDs.timeBased());
        scv.setVotes_count(c.getNumber_of_votes());
        mapper.save(scv);
    }

    public void save(BPlanSingleCandidateVote c) {
        mapper.save(c);
    }
}
