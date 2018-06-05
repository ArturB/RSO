package org.voting.gateway.repository;

import com.datastax.driver.core.utils.UUIDs;
import com.datastax.driver.mapping.Mapper;
import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.*;
import org.voting.gateway.service.VotesDesignationPackDTO;
import org.voting.gateway.service.VotesDesignationSingleCandidateDTO;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class VotesDesignationPackRepository {

    private final TurnRepository turnRepository;
    private final ElectoralDistrictRepository electoralDistrictRepository;
    private final VotingDataRepository votingDataRepository;
    private final VotesFromDistrictRepository votesFromDistrictRepository;
    private final ElectoralPeriodsRepository electoralPeriodsRepository;
    private final SmallUserRepository smallUserRepository;


    public VotesDesignationPackRepository(TurnRepository turnRepository, ElectoralDistrictRepository electoralDistrictRepository,
                                          VotingDataRepository votingDataRepository, VotesFromDistrictRepository votesFromDistrictRepository,
                                          ElectoralPeriodsRepository electoralPeriodsRepository, SmallUserRepository smallUserRepository) {
        this.turnRepository = turnRepository;
        this.electoralDistrictRepository = electoralDistrictRepository;
        this.votingDataRepository = votingDataRepository;
        this.votesFromDistrictRepository = votesFromDistrictRepository;
        this.electoralPeriodsRepository = electoralPeriodsRepository;
        this.smallUserRepository = smallUserRepository;
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

        //kasowanie

        long numVotesInPack = votesFromDistrict.stream()
            .filter(v-> v.getDate().equals(votesPack.getDate()))
            .map(v->{
                votesFromDistrictRepository.delete(v.getId());
                return v;
            }).count();

        if(numVotesInPack < 1 ) throw new RuntimeException("Voting Pack doesnt exist");

        //wprowadzanie nowych wartosci

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

    public VotesDesignationPackDTO findOneByTurnByUser(UUID turnId, UUID userId) {

        SmallUser smallUser = smallUserRepository.findOne(userId);
        if( smallUser == null) throw new RuntimeException("User "+ userId +" doesnt exist");

        if( smallUser.getElectoralDistrictId() == null) throw new RuntimeException("User "+ userId +" has no district");

        //ElectoralDistrict district = electoralDistrictRepository.findOne(smallUser.getElectoralDistrictId());

        List<VotingData> votingDataList = votingDataRepository.findInDistrictInTurn(smallUser.getElectoralDistrictId(),turnId);
        if(votingDataList.size() != 1) throw new RuntimeException("No voting data");

        VotingData votingData = votingDataList.get(0);

        List<VotesFromDistrict> votesFromDistrict = votesFromDistrictRepository.findByUserByVotingData(userId,votingData.getId());

        if(votesFromDistrict.isEmpty()) return null;



        VotesDesignationPackDTO votesDesignationPackDTO = new VotesDesignationPackDTO();
        votesDesignationPackDTO.setUserId(userId);
        votesDesignationPackDTO.setElectoralDistrictId(smallUser.getElectoralDistrictId());
        votesDesignationPackDTO.setTooManyMarksCardsUsed(
            votesFromDistrict.stream()
            .filter(v -> v.getType().equals("TOO MANY"))
            .mapToInt(v-> v.getNumberOfVotes()).sum()
        );
        votesDesignationPackDTO.setNoneMarksCardsUsed(
            votesFromDistrict.stream()
                .filter(v -> v.getType().equals("NONE"))
                .mapToInt(v-> v.getNumberOfVotes()).sum()
        );
        votesDesignationPackDTO.setErasedMarksCardsUsed(
            votesFromDistrict.stream()
                .filter(v -> v.getType().equals("ERASED"))
                .mapToInt(v-> v.getNumberOfVotes()).sum()

        );

        Map<UUID,Integer> votesForCandidate =
            votesFromDistrict.stream()
                .filter(v -> v.getType().equals("VALID"))
                .collect(Collectors.groupingBy(VotesFromDistrict::getCandidateId, Collectors.summingInt(VotesFromDistrict::getNumberOfVotes)));


        List<VotesDesignationSingleCandidateDTO> listVotesForCandidate = votesForCandidate.keySet().stream()
            .map(v-> {
                VotesDesignationSingleCandidateDTO voteSingle = new VotesDesignationSingleCandidateDTO();
                voteSingle.setCandidate_id(v);
                voteSingle.setNumber_of_votes(votesForCandidate.get(v));
                return voteSingle;
            }).collect(Collectors.toList());

        votesDesignationPackDTO.setCandidate_votes(listVotesForCandidate);

        return votesDesignationPackDTO;
    }
}
