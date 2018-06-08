package org.voting.gateway.repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;
import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.Municipality;
import org.voting.gateway.domain.Turn;
import org.voting.gateway.service.MunicipalityDTO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public List<MunicipalityDTO> findAllDTO() {
        return findAll().stream().map(MunicipalityDTO::new).collect(Collectors.toList());
    }

    public MunicipalityDTO findOneDTO(UUID id) {
        Municipality municipality = findOne(id);
        return new MunicipalityDTO(municipality);
    }

    public void save(Municipality municipality) {
        mapper.save(municipality);
    }
}
