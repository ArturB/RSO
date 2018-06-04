package org.voting.gateway.repository;


import com.datastax.driver.mapping.Mapper;
import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.VotesFromDistrict;

@Repository
public class VotesFromDistrictRepository {

    private Mapper<VotesFromDistrict> mapper;
    private final CassandraSession cassandraSession;


    public VotesFromDistrictRepository(CassandraSession cassandraSession) {
        this.cassandraSession = cassandraSession;
        mapper = cassandraSession.getMappingManager().mapper(VotesFromDistrict.class);
    }




}
