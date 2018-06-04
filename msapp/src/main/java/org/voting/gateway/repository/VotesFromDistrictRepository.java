package org.voting.gateway.repository;


import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;
import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.VotesFromDistrict;

import java.util.List;
import java.util.UUID;

@Repository
public class VotesFromDistrictRepository {

    private Mapper<VotesFromDistrict> mapper;
    private final CassandraSession cassandraSession;


    public VotesFromDistrictRepository(CassandraSession cassandraSession) {
        this.cassandraSession = cassandraSession;
        mapper = cassandraSession.getMappingManager().mapper(VotesFromDistrict.class);
    }


    public void save(VotesFromDistrict votes) {
        mapper.save(votes);
    }

    public List<VotesFromDistrict> findByUserByVotingData(UUID userId, UUID vdId) {

        Statement statement = new SimpleStatement("SELECT * FROM votes_from_ward " +
            "WHERE user = ? AND voting_data = ?", userId, vdId);

        ResultSet results = cassandraSession.getSession().execute(statement);
        Result<VotesFromDistrict> votes = mapper.map(results);
        return votes.all();


    }

    public void delete(UUID id) {
        mapper.delete(id);
    }
}
