package org.voting.gateway.repository;

import com.datastax.driver.mapping.Mapper;
import org.springframework.stereotype.Repository;

import org.voting.gateway.domain.SmallUser;

@Repository
public class SmallUserRepository {

    private Mapper<SmallUser> mapper;
    private final CassandraSession cassandraSession;


    public SmallUserRepository(CassandraSession cassandraSession) {
        this.cassandraSession = cassandraSession;
        mapper = cassandraSession.getMappingManager().mapper(SmallUser.class);
    }

}
