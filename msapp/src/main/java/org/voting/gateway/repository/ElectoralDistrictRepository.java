package org.voting.gateway.repository;


import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.utils.UUIDs;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;
import org.springframework.stereotype.Repository;

import org.voting.gateway.domain.ElectoralDistrict;
import org.voting.gateway.domain.SmallUser;
import org.voting.gateway.domain.Turn;
import org.voting.gateway.domain.VotingData;
import org.voting.gateway.service.ElectoralDistrictDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class ElectoralDistrictRepository {

    private Mapper<ElectoralDistrict> mapper;
    private final CassandraSession cassandraSession;
    private final SmallUserRepository smallUserRepository;
    private final MunicipalityRepository municipalityRepository;


    public ElectoralDistrictRepository(CassandraSession cassandraSession,
                                        SmallUserRepository smallUserRepository, MunicipalityRepository municipalityRepository) {
        this.cassandraSession = cassandraSession;
        this.smallUserRepository = smallUserRepository;
        mapper = cassandraSession.getMappingManager().mapper(ElectoralDistrict.class);
        this.municipalityRepository = municipalityRepository;
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
        List<SmallUser> usersInDistrict = smallUserRepository.findInDistrict(id);
        if(!usersInDistrict.isEmpty()) throw new RuntimeException("Cant delete district: " + id + " users present");
        mapper.delete(id);
    }

    public List<ElectoralDistrict> findInMunicipality(UUID municipalityId) {
        ResultSet results = cassandraSession.getSession().execute("SELECT * FROM ward WHERE commune = ? ALLOW " +
                "FILTERING",
            municipalityId);
        Result<ElectoralDistrict> districts = mapper.map(results);
        return districts.all();
    }

    public ElectoralDistrictDTO findOneDTO(UUID id) {
        return new ElectoralDistrictDTO(mapper.get(id));

    }

    public List<ElectoralDistrictDTO> findInMunicipalityDTO(UUID municipalityId) {
        ResultSet results = cassandraSession.getSession().execute("SELECT * FROM ward WHERE commune = ? ALLOW " +
                "FILTERING",
            municipalityId);
        Result<ElectoralDistrict> districts = mapper.map(results);
        return districts.all().stream().map(ElectoralDistrictDTO::new).collect(Collectors.toList());
    }

    public void create(ElectoralDistrict electoralDistrict) {
        save(electoralDistrict);
    }
}
