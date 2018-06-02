package org.voting.gateway.repository;


import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;
import org.springframework.stereotype.Repository;

import org.voting.gateway.domain.ElectoralDistrict;

import java.util.List;
import java.util.UUID;

@Repository
public class ElectoralDistrictRepository {

    private Mapper<ElectoralDistrict> mapper;
    private final CassandraSession cassandraSession;


    public ElectoralDistrictRepository(CassandraSession cassandraSession) {
        this.cassandraSession = cassandraSession;
        mapper = cassandraSession.getMappingManager().mapper(ElectoralDistrict.class);
    }


    public ElectoralDistrict save(ElectoralDistrict electoralDistrict) {
        mapper.save(electoralDistrict);
        return electoralDistrict;
    }

    public List<ElectoralDistrict> findAll() {
        ResultSet results = cassandraSession.getSession().execute("SELECT * FROM ward");
        Result<ElectoralDistrict> districts = mapper.map(results);
        return districts.all();
    }

    public ElectoralDistrict findOne(UUID id) {
        ElectoralDistrict res = mapper.get(id);
        return res;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public List<ElectoralDistrict> findInMunicipality(UUID municipalityId) {
        ResultSet results = cassandraSession.getSession().execute("SELECT * FROM ward WHERE municipality_id = ?", municipalityId);
        Result<ElectoralDistrict> districts = mapper.map(results);
        return districts.all();
    }
}
