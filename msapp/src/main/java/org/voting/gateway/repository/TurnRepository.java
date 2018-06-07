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
        ResultSet results = cassandraSession.getSession().execute("SELECT * FROM turn WHERE commune = ? ALLOW " +
            "FILTERING" , municipalityId);
        Result<Turn> turns = mapper.map(results);
        return turns.all();
    }

    public Turn findOne(UUID id) {
        Turn res = mapper.get(id);
        return res;

    }

    public void save(Turn turn) {
        mapper.save(turn);
    }

    public Turn findInMunicipalityByNumber(UUID municipalityId,int turnNumber) {
        ResultSet results = cassandraSession.getSession().execute("SELECT * FROM turn WHERE commune = ? ALLOW " +
                "FILTERING" , municipalityId);
        Result<Turn> resTurns = mapper.map(results);
        List<Turn> turns = resTurns.all();

        if(turns.isEmpty()) throw new RuntimeException("Brak tur dla gminy");

        if(turnNumber == 1)
        {
           return turns.stream()
                .filter(t-> !t.isLastTurn())
                .findFirst().get();
        }
        else if(turnNumber == 2)
        {
            if(turns.size() != 2) throw new RuntimeException("Nie ma 2 tur w gmminie");

            return turns.stream()
                .filter(t-> t.isLastTurn())
                .findFirst().get();
        }
        else throw new RuntimeException("Zly numer tury");


    }

    public UUID findUUIDInMunicipalityByNumber(UUID municipalityId,int turnNumber) {
        return findInMunicipalityByNumber(municipalityId,turnNumber).getId();
    }
}
