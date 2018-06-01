package org.voting.gateway.repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;
import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.Candidate;

import java.util.List;
import java.util.UUID;


@Repository
public class CandidateRepository {

    private Mapper<Candidate> mapper;
    private final CassandraSession cassandraSession;


    public CandidateRepository(CassandraSession cassandraSession) {
        this.cassandraSession = cassandraSession;
        mapper = cassandraSession.getMappingManager().mapper(Candidate.class);
    }


    public Candidate save(Candidate candidate)
    {
        mapper.save(candidate);
        return candidate;
    }

    public List<Candidate> findAll()
    {
        ResultSet results = cassandraSession.getSession().execute("SELECT * FROM candidate");
        Result<Candidate> candidates = mapper.map(results);
        return candidates.all();

    }

    public Candidate findOne(UUID id)
    {
        Candidate res = mapper.get(id);
        return res;
    }

    public void delete(UUID id)
    {
        mapper.delete(id);
    }

    public List<Candidate> findInMunicipality(UUID municipalityId)
    {
        ResultSet results = cassandraSession.getSession().execute("SELECT * FROM candidate WHERE municipality_id = " + municipalityId);
        Result<Candidate> candidates = mapper.map(results);
        return candidates.all();
    }


    public List<Candidate> findInMunicipalityByTurn(UUID municipalityId, UUID turn) {
        ResultSet results = cassandraSession.getSession()
            .execute("SELECT * FROM candidate " +
                "WHERE municipality_id = " + municipalityId +
                "AND turns CONTAINS " + turn);
        Result<Candidate> candidates = mapper.map(results);
        return candidates.all();
    }
}
