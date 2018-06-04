package org.voting.gateway.repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;
import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.Turn;

import java.util.List;
import java.util.UUID;

@Repository
public class TurnRepository {

    private Mapper<Turn> mapper;
    private final CassandraSession cassandraSession;


    public TurnRepository(CassandraSession cassandraSession) {
        this.cassandraSession = cassandraSession;
        mapper = cassandraSession.getMappingManager().mapper(Turn.class);
    }

    public List<Turn> findAll() {
        ResultSet results = cassandraSession.getSession().execute("SELECT * FROM turn");
        Result<Turn> turns = mapper.map(results);
        return turns.all();
    }


    public List<Turn> findInMunicipality(UUID municipalityId) {
        ResultSet results = cassandraSession.getSession().execute("SELECT * FROM turn WHERE commune = ?" , municipalityId);
        Result<Turn> turns = mapper.map(results);
        return turns.all();
    }
}
