package org.voting.gateway.repository;


import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;
import org.springframework.stereotype.Repository;

import org.voting.gateway.domain.ElectoralDistrict;
import org.voting.gateway.domain.Turn;
import org.voting.gateway.domain.VotingData;
import org.voting.gateway.service.ElectoralDistrictDTO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class ElectoralDistrictRepository {

    private Mapper<ElectoralDistrict> mapper;
    private final CassandraSession cassandraSession;
    private final VotingDataRepository votingDataRepository;
    private final TurnRepository turnRepository;


    public ElectoralDistrictRepository(CassandraSession cassandraSession,VotingDataRepository votingDataRepository,
                                       TurnRepository turnRepository) {
        this.cassandraSession = cassandraSession;
        this.votingDataRepository = votingDataRepository;
        this.turnRepository = turnRepository;
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

    public ElectoralDistrictDTO findOneDTO(UUID id) {

        ElectoralDistrict electoralDistrict = findOne(id);
        List<VotingData> votingData = votingDataRepository.findInDistrict(id);
        List<Turn> turns = turnRepository.findAll();
        if(votingData.size() > 2 ) throw new RuntimeException("Too many turns for District:" + electoralDistrict.getElectoralDistrictName());
        ElectoralDistrictDTO electoralDistrictDTO = new ElectoralDistrictDTO(electoralDistrict);

        votingData.stream()
            .forEach(s->{
                if(turns.stream()
                    .filter(t-> t.getId() == s.getWard())
                    .findFirst().get().isLastTurn())
                {
                    electoralDistrictDTO.setSecond_round_votes_accepted(s.isVotingFinished());
                }
                else
                {
                    electoralDistrictDTO.setFirst_round_votes_accepted(s.isVotingFinished());
                }
            });

        return electoralDistrictDTO;






    }

    public List<ElectoralDistrictDTO> findInMunicipalityDTO(UUID municipalityId) {

        List<ElectoralDistrict> electoralDistricts = findInMunicipality(municipalityId);
        List<VotingData> votingData = votingDataRepository.findAll();
        List<Turn> turns = turnRepository.findAll();

        List<ElectoralDistrictDTO> result = electoralDistricts.stream()
            .map(s->{

                ElectoralDistrictDTO electoralDistrictDTO = new ElectoralDistrictDTO(s);

                votingData.stream()
                    .filter(v -> v.getWard() == s.getId())
                    .forEach(v ->
                    {
                        if(turns.stream()
                            .filter(t-> t.getId() == v.getWard())
                            .findFirst().get().isLastTurn())
                        {
                            electoralDistrictDTO.setSecond_round_votes_accepted(v.isVotingFinished());
                        }
                        else
                        {
                            electoralDistrictDTO.setFirst_round_votes_accepted(v.isVotingFinished());
                        }

                    });

                return electoralDistrictDTO;
            }).collect(Collectors.toList());



        return result;
    }
}
