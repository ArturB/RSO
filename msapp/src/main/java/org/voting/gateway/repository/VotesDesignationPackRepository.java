package org.voting.gateway.repository;

import com.datastax.driver.core.utils.UUIDs;
import com.datastax.driver.mapping.Mapper;
import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.*;
import org.voting.gateway.service.VotesDesignationPackDTO;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class VotesDesignationPackRepository {

    private final TurnRepository turnRepository;
    private final ElectoralDistrictRepository electoralDistrictRepository;
    private final VotingDataRepository votingDataRepository;
    private final VotesFromDistrictRepository votesFromDistrictRepository;
    private final ElectoralPeriodsRepository electoralPeriodsRepository;


    public VotesDesignationPackRepository(TurnRepository turnRepository, ElectoralDistrictRepository electoralDistrictRepository,
                                          VotingDataRepository votingDataRepository, VotesFromDistrictRepository votesFromDistrictRepository,
                                          ElectoralPeriodsRepository electoralPeriodsRepository) {
        this.turnRepository = turnRepository;
        this.electoralDistrictRepository = electoralDistrictRepository;
        this.votingDataRepository = votingDataRepository;
        this.votesFromDistrictRepository = votesFromDistrictRepository;
        this.electoralPeriodsRepository = electoralPeriodsRepository;
    }

    public void add(VotesDesignationPackDTO votesPack) {


        ElectoralDistrict electoralDistrict = electoralDistrictRepository.findOne(votesPack.getElectoralDistrictId());
        List<Turn> turns = turnRepository.findInMunicipality(electoralDistrict.getMunicipalityId());
        List<ElectoralPeriod> electoralPeriods = electoralPeriodsRepository.findAll();
        Date time = new Date();
        ElectoralPeriod ePeriod = electoralPeriods.stream()
            .filter(p -> (time.compareTo(p.getStartDate()) > 0 && time.compareTo(p.getEndDate()) < 0 ))
            .findFirst()
            .orElseThrow(() ->  new RuntimeException("No ElectoralPeriod"));

        boolean isSecondTurn;

        if(ePeriod.getName().equals("MidRoundPeriod")) isSecondTurn = false;
        else if(ePeriod.getName().equals("PostElectionPeriod")) isSecondTurn = true;
        else throw new RuntimeException("Not possible in this ElectoralPeriod");


        Optional<Turn> turn = turns.stream()
            .filter(t-> t.isLastTurn() == isSecondTurn)
            .findFirst();
        if(!turn.isPresent()) throw new RuntimeException("Turn not found");

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

    public void edit(VotesDesignationPackDTO votesPack) {


        ElectoralDistrict electoralDistrict = electoralDistrictRepository.findOne(votesPack.getElectoralDistrictId());
        List<Turn> turns = turnRepository.findInMunicipality(electoralDistrict.getMunicipalityId());
        List<ElectoralPeriod> electoralPeriods = electoralPeriodsRepository.findAll();
        Date time = new Date();
        ElectoralPeriod ePeriod = electoralPeriods.stream()
            .filter(p -> (time.compareTo(p.getStartDate()) > 0 && time.compareTo(p.getEndDate()) < 0 ))
            .findFirst()
            .orElseThrow(() ->  new RuntimeException("No ElectoralPeriod"));

        boolean isSecondTurn;

        if(ePeriod.getName().equals("MidRoundPeriod")) isSecondTurn = false;
        else if(ePeriod.getName().equals("PostElectionPeriod")) isSecondTurn = true;
        else throw new RuntimeException("Not possible in this ElectoralPeriod");


        Optional<Turn> turn = turns.stream()
            .filter(t-> t.isLastTurn() == isSecondTurn)
            .findFirst();
        if(!turn.isPresent()) throw new RuntimeException("Turn not found");

        List<VotingData> votingDataList = votingDataRepository.findInDistrictInTurn(electoralDistrict.getId(),turn.get().getId());
        if(votingDataList.size() != 1) throw new RuntimeException("No voting data");

        VotingData votingData = votingDataList.get(0);

        if(votingData.isVotingFinished()) throw new RuntimeException("Voting ended");

        List<VotesFromDistrict> votesFromDistrict = votesFromDistrictRepository.findByUserByVotingData(votesPack.getUserId(),votingData.getId());

        




    }
}
