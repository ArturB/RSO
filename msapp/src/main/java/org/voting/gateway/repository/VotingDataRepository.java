package org.voting.gateway.repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;
import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.VotingData;

import java.util.List;
import java.util.UUID;

@Repository
public class VotingDataRepository {

    private Mapper<VotingData> mapper;
    private final CassandraSession cassandraSession;


    public VotingDataRepository(CassandraSession cassandraSession) {
        this.cassandraSession = cassandraSession;
        mapper = cassandraSession.getMappingManager().mapper(VotingData.class);
    }

    public List<VotingData> findInDistrict(UUID id) {
        ResultSet results = cassandraSession.getSession().execute("SELECT * FROM voting_data WHERE ward = ?", id);
        Result<VotingData> votingData = mapper.map(results);
        return votingData.all();
    }

    public List<VotingData> findInDistrictInTurn(UUID id,UUID tId) {
        ResultSet results = cassandraSession.getSession().execute("SELECT * FROM voting_data WHERE ward = ? AND turns = ?", id , tId);
        Result<VotingData> votingData = mapper.map(results);
        return votingData.all();
    }

    public List<VotingData> findAll() {
        ResultSet results = cassandraSession.getSession().execute("SELECT * FROM voting_data");
        Result<VotingData> votingData = mapper.map(results);
        return votingData.all();
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void save(VotingData votingData) {
        mapper.save(votingData);
    }
}
