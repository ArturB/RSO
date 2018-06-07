package org.voting.gateway.repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;
import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.Party;

import java.util.List;
import java.util.UUID;

@Repository
public class PartyRepository {
    private Mapper<Party> mapper;
    private final CassandraSession cassandraSession;


    public PartyRepository(CassandraSession cassandraSession) {
        this.cassandraSession = cassandraSession;
        mapper = cassandraSession.getMappingManager().mapper(Party.class);
    }


    public List<Party> findAll()
    {
        ResultSet results = cassandraSession.getSession().execute("SELECT * FROM party");
        Result<Party> parties = mapper.map(results);
        return parties.all();
    }

    public Party findOne(UUID id) {
        Party res = mapper.get(id);
        return res;
    }

    public Party save(Party party) {
        mapper.save(party);
        return party;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }
}
