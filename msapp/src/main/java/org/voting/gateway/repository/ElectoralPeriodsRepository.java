package org.voting.gateway.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.ElectoralPeriod;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;

@Repository
public class ElectoralPeriodsRepository {

    private Mapper<ElectoralPeriod> mapper;
    private final CassandraSession cassandraSession;

    public ElectoralPeriodsRepository(CassandraSession cassandraSession) {
        this.cassandraSession = cassandraSession;
        mapper = cassandraSession.getMappingManager().mapper(ElectoralPeriod.class);
    }

    public List<ElectoralPeriod> findAll() {
        ResultSet results = cassandraSession.getSession().execute("SELECT * FROM electoral_period");
        Result<ElectoralPeriod> districts = mapper.map(results);
        return districts.all();
    }

    public void update(ElectoralPeriod period) {
        mapper.save(period);
    }
}
