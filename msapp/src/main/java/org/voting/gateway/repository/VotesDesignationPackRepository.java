package org.voting.gateway.repository;

import com.datastax.driver.core.utils.UUIDs;
import com.datastax.driver.mapping.Mapper;
import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.*;
import org.voting.gateway.service.VotesDesignationPackDTO;

import java.util.List;
import java.util.Optional;

@Repository
public class VotesDesignationPackRepository {

    private final TurnRepository turnRepository;
    private final ElectoralDistrictRepository electoralDistrictRepository;
    private final VotingDataRepository votingDataRepository;
    private final VotesFromDistrictRepository votesFromDistrictRepository;
    private final SmallUserRepository smallUserRepository;


    public VotesDesignationPackRepository(TurnRepository turnRepository, ElectoralDistrictRepository electoralDistrictRepository,
                                          VotingDataRepository votingDataRepository, VotesFromDistrictRepository votesFromDistrictRepository,
                                          SmallUserRepository smallUserRepository) {
        this.turnRepository = turnRepository;
        this.electoralDistrictRepository = electoralDistrictRepository;
        this.votingDataRepository = votingDataRepository;
        this.votesFromDistrictRepository = votesFromDistrictRepository;
        this.smallUserRepository = smallUserRepository;
    }

    public void add(VotesDesignationPackDTO votesPack) {

        //SmallUser smallUser = smallUserRepository.findOne(votesPack.getUserId());
        ElectoralDistrict electoralDistrict = electoralDistrictRepository.findOne(votesPack.getElectoralDistrictId());
        List<Turn> turns = turnRepository.findInMunicipality(electoralDistrict.getMunicipalityId());
        Optional<Turn> turn = turns.stream()
            .filter(t-> (t.getDateFrom().getDaysSinceEpoch() < votesPack.getDate().getDaysSinceEpoch() &&
                t.getDateTo().getDaysSinceEpoch() > votesPack.getDate().getDaysSinceEpoch()))
            .findFirst();
        if(!turn.isPresent()) throw new RuntimeException("No turn in progress");

        List<VotingData> votingDataList = votingDataRepository.findInDistrictInTurn(electoralDistrict.getId(),turn.get().getId());
        if(votingDataList.size() != 1) throw new RuntimeException("No voting data");

        VotingData votingData = votingDataList.get(0);

        if(votingData.isVotingFinished()) throw new RuntimeException("Voting ended");

        votesPack.getCandidate_votes().stream()
            .forEach(c ->{
                VotesFromDistrict votes = new VotesFromDistrict(UUIDs.timeBased(),
                    c.getCandidate_id(),votesPack.getDate(),votingData.getId(),
                    c.getNumber_of_votes(),"VALID",votesPack.getUserId());
                votesFromDistrictRepository.save(votes);
            });

        VotesFromDistrict votesTooMany = new VotesFromDistrict(UUIDs.timeBased(),
            null,votesPack.getDate(),votingData.getId(),
            votesPack.getTooManyMarksCardsUsed(),"TOO MANY",votesPack.getUserId());

        votesFromDistrictRepository.save(votesTooMany);

        VotesFromDistrict votesNone = new VotesFromDistrict(UUIDs.timeBased(),
            null,votesPack.getDate(),votingData.getId(),
            votesPack.getNoneMarksCardsUsed(),"NONE",votesPack.getUserId());

        votesFromDistrictRepository.save(votesNone);

        VotesFromDistrict votesErased = new VotesFromDistrict(UUIDs.timeBased(),
            null,votesPack.getDate(),votingData.getId(),
            votesPack.getErasedMarksCardsUsed(),"ERASED",votesPack.getUserId());

        votesFromDistrictRepository.save(votesErased);








    }
}
