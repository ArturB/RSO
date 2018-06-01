package org.voting.gateway.repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;
import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.Municipality;

import java.util.List;
import java.util.UUID;

@Repository
public class MunicipalityRepository {

    private Mapper<Municipality> mapper;
    private final CassandraSession cassandraSession;


    public MunicipalityRepository(CassandraSession cassandraSession) {
        this.cassandraSession = cassandraSession;
        mapper = cassandraSession.getMappingManager().mapper(Municipality.class);
    }


    public List<Municipality> findAll() {
        ResultSet results = cassandraSession.getSession().execute("SELECT * FROM commune");
        Result<Municipality> candidates = mapper.map(results);
        return candidates.all();
    }

    public Municipality findOne(UUID id) {
        Municipality res = mapper.get(id);
        return res;
    }
}
