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
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class ElectoralDistrictRepository {

    private Mapper<ElectoralDistrict> mapper;
    private final CassandraSession cassandraSession;
    private final VotingDataRepository votingDataRepository;
    private final TurnRepository turnRepository;
    private final SmallUserRepository smallUserRepository;


    public ElectoralDistrictRepository(CassandraSession cassandraSession,VotingDataRepository votingDataRepository,
                                       TurnRepository turnRepository, SmallUserRepository smallUserRepository) {
        this.cassandraSession = cassandraSession;
        this.votingDataRepository = votingDataRepository;
        this.turnRepository = turnRepository;
        this.smallUserRepository = smallUserRepository;
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
        List<SmallUser> usersInDistrict = smallUserRepository.findInDistrict(id);
        if(!usersInDistrict.isEmpty()) throw new RuntimeException("Cant delete district: " + id + " users present");

        List<VotingData> votingData = votingDataRepository.findInDistrict(id);
        if(votingData.size() > 1 ) throw new RuntimeException("Cant delete district: " + id + " second turn started");
        if(!votingData.isEmpty()) {
            votingData.stream()
                .forEach(v->
                {
                    votingDataRepository.delete(v.getId());
                });
        }
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

    public void create(ElectoralDistrict electoralDistrict) {

        List<Turn> turns =  turnRepository.findInMunicipality(electoralDistrict.getMunicipalityId());
        if(turns.size() > 1 ) throw new RuntimeException("Cant create district: " + electoralDistrict.getElectoralDistrictName() + " second turn started");
        if(turns.isEmpty()) throw new RuntimeException("Cant create district: " + electoralDistrict.getElectoralDistrictName() + " no first turn");
        VotingData votingData = new VotingData();
        votingData.setId(UUIDs.timeBased());
        votingData.setTurn(turns.get(0).getId());
        votingData.setWard(electoralDistrict.getId());
        votingData.setVotingFinished(false);
        votingData.setNoCardsUsed(0);
        votingData.setNoCardsUsed(-1);

        save(electoralDistrict);
        votingDataRepository.save(votingData);
    }
}
