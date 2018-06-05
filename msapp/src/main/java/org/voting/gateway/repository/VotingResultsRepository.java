package org.voting.gateway.repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;
import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.VotingReport;
import org.voting.gateway.domain.VotingResults;

import java.util.List;
import java.util.UUID;

@Repository
public class VotingResultsRepository {

    private Mapper<VotingResults> mapper;
    private final CassandraSession cassandraSession;

    public VotingResultsRepository(CassandraSession cassandraSession) {
        this.cassandraSession = cassandraSession;
        mapper = cassandraSession.getMappingManager().mapper(VotingResults.class);
    }


    public List<VotingResults> findByMunicipalityByRound(UUID municipalityId, UUID round) {

        Statement statement = new SimpleStatement("SELECT * FROM voting_results " +
            "WHERE commune_id = ? AND turn_id = ?", municipalityId, round);

        ResultSet results = cassandraSession.getSession().execute(statement);
        Result<VotingResults> votes = mapper.map(results);
        return votes.all();


    }
}
