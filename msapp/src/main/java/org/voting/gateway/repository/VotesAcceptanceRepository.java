package org.voting.gateway.repository;

import com.datastax.driver.core.utils.UUIDs;
import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.*;

import com.datastax.driver.mapping.Mapper;
import org.voting.gateway.service.VotesAcceptBodyDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class VotesAcceptanceRepository {



    private final VotesFromDistrictRepository votesFromDistrictRepository;
    private final VotingDataRepository votingDataRepository;
    private final VotingReportRepository votingReportRepository;
    private final TurnRepository turnRepository;
    private final VotingResultsRepository votingResultsRepository;
    private final ElectoralPeriodsRepository electoralPeriodsRepository;

    public VotesAcceptanceRepository(VotesFromDistrictRepository votesFromDistrictRepository, VotingDataRepository votingDataRepository,
                                     TurnRepository turnRepository,VotingReportRepository votingReportRepository,
                                     VotingResultsRepository votingResultsRepository, ElectoralPeriodsRepository electoralPeriodsRepository) {

        this.votesFromDistrictRepository = votesFromDistrictRepository;
        this.votingDataRepository = votingDataRepository;
        this.turnRepository = turnRepository;
        this.votingReportRepository = votingReportRepository;
        this.votingResultsRepository = votingResultsRepository;
        this.electoralPeriodsRepository= electoralPeriodsRepository;


    }

    public void acceptDistrict(UUID districtId, UUID round, VotesAcceptBodyDTO votesAcceptBody) {

        List<VotingData> votingDataList = votingDataRepository.findInDistrictInTurn(districtId,round);
        if(votingDataList.size() != 1) throw new RuntimeException("Too many voting data");

        VotingData votingData = votingDataList.get(0);

        List<VotesFromDistrict> votesFromDistrict = votesFromDistrictRepository.findByVotingData(votingData.getId());

        if(votesFromDistrict.isEmpty()) throw new RuntimeException("No votes in district");

        votingData.setVotingFinished(true);
        votingData.setNoCardsUsed(votesAcceptBody.getNo_cards_used());
        votingData.setNoCanVote(votesAcceptBody.getNo_can_vote());

        votingDataRepository.save(votingData);

        Map<String,List<VotesFromDistrict>> voteMap = votesFromDistrict.stream()
            .collect(Collectors.groupingBy(VotesFromDistrict::getType));

        if(voteMap.containsKey("TOO MANY")) {
            VotingReport tooManyReport = new VotingReport(districtId, round, votingData , "TOO MANY");

            tooManyReport.setNoOfVotes(voteMap.get("TOO MANY").stream()
                .mapToInt(r -> r.getNumberOfVotes()).sum());

            votingReportRepository.save(tooManyReport);
        }

        if(voteMap.containsKey("NONE")) {
            VotingReport noneReport = new VotingReport(districtId, round, votingData , "NONE");

            noneReport.setNoOfVotes(voteMap.get("NONE").stream()
                .mapToInt(r -> r.getNumberOfVotes()).sum());

            votingReportRepository.save(noneReport);
        }

        if(voteMap.containsKey("ERASED")){
            VotingReport erasedReport = new VotingReport(districtId, round, votingData , "ERASED");

            erasedReport.setNoOfVotes(voteMap.get("ERASED").stream()
                .mapToInt(r -> r.getNumberOfVotes()).sum());

            votingReportRepository.save(erasedReport);
        }

        if(voteMap.containsKey("VALID"))
        {
            Map<UUID,List<VotesFromDistrict>> voteForCandidateMap = voteMap.get("VALID").stream()
                .collect(Collectors.groupingBy(VotesFromDistrict::getCandidateId));

            voteForCandidateMap.keySet().stream()
                .forEach(k ->{
                    int sumForCan = voteForCandidateMap.get(k).stream()
                        .mapToInt(v-> v.getNumberOfVotes())
                        .sum();
                    VotingReport report = new VotingReport(districtId, round, votingData , "VALID");

                    report.setNoOfVotes(sumForCan);
                    report.setCandidateId(k);

                    votingReportRepository.save(report);
                });

        }

    }

    public void acceptMunicipality(UUID municipalityId, UUID round) {

        Turn turn = turnRepository.findOne(round);
        List<VotingData> votingDataList = votingDataRepository.findInTurn(round);

        List<VotesFromDistrict> allVotes = votesFromDistrictRepository.findAll();

        List<VotesFromDistrict> votesFromMunicipality = allVotes.stream()
            .filter(v-> votingDataList.stream()
                .anyMatch(d-> d.getId().equals(v.getVotingDataId())))
            .collect(Collectors.toList());

        if(votesFromMunicipality.isEmpty()) throw new RuntimeException("No votes in district");

        turn.setTurnFinished(true);
        turnRepository.save(turn);

        int noCardUsed = votingDataList.stream()
            .mapToInt(v-> v.getNoCardsUsed()).sum();
        int noCanVote = votingDataList.stream()
            .mapToInt(v-> v.getNoCanVote()).sum();



        Map<String,List<VotesFromDistrict>> voteMap = votesFromMunicipality.stream()
            .collect(Collectors.groupingBy(VotesFromDistrict::getType));

        if(voteMap.containsKey("TOO MANY")) {
            VotingResults tooManyReport = new VotingResults(municipalityId, round, noCardUsed, noCanVote, "TOO MANY");

            tooManyReport.setNoOfVotes(voteMap.get("TOO MANY").stream()
                .mapToInt(r -> r.getNumberOfVotes()).sum());

            votingResultsRepository.save(tooManyReport);

        }

        if(voteMap.containsKey("NONE")) {
            VotingResults noneReport = new VotingResults(municipalityId, round, noCardUsed, noCanVote, "NONE");

            noneReport.setNoOfVotes(voteMap.get("NONE").stream()
                .mapToInt(r -> r.getNumberOfVotes()).sum());

            votingResultsRepository.save(noneReport);
        }

        if(voteMap.containsKey("ERASED")){
            VotingResults erasedReport = new VotingResults(municipalityId, round, noCardUsed, noCanVote, "ERASED");

            erasedReport.setNoOfVotes(voteMap.get("ERASED").stream()
                .mapToInt(r -> r.getNumberOfVotes()).sum());

            votingResultsRepository.save(erasedReport);
        }

        if(voteMap.containsKey("VALID"))
        {
            int numberOfVotes = voteMap.get("VALID").stream()
                .mapToInt(v-> v.getNumberOfVotes()).sum();

            int halfVotes = numberOfVotes/2;

            Map<UUID,List<VotesFromDistrict>> voteForCandidateMap = voteMap.get("VALID").stream()
                .collect(Collectors.groupingBy(VotesFromDistrict::getCandidateId));

            Boolean a = new Boolean(true);


            List<VotingResults> results = voteForCandidateMap.keySet().stream()
                .map(k ->{
                    int sumForCan = voteForCandidateMap.get(k).stream()
                        .mapToInt(v-> v.getNumberOfVotes())
                        .sum();
                    VotingResults report = new VotingResults(municipalityId, round, noCardUsed, noCanVote, "VALID");

                    report.setNoOfVotes(sumForCan);
                    report.setCandidateId(k);
                    return report;

                }).collect(Collectors.toList());

            //jesli jest druga tura
            if(!turn.isLastTurn() && results.stream().anyMatch(r-> r.getNoOfVotes() > halfVotes)) {

                ElectoralPeriod secTurnPeriod = electoralPeriodsRepository.findAll().stream()
                    .filter(e -> e.getName().equals("SecondRoundPeriod"))
                    .findFirst().get();

                Turn secTurn = new Turn();
                secTurn.setId(UUIDs.timeBased());
                secTurn.setLastTurn(true);
                secTurn.setTurnFinished(false);
                secTurn.setCommune(municipalityId);
                secTurn.setDateFrom(secTurnPeriod.getStartDate());
                secTurn.setDateTo(secTurnPeriod.getEndDate());
                turnRepository.save(secTurn);

                votingDataList.stream()
                    .forEach(v->{
                        VotingData temp = new VotingData();
                        temp.setId(UUIDs.timeBased());
                        temp.setVotingFinished(false);
                        temp.setWard(v.getWard());
                        temp.setTurn(secTurn.getId());

                        votingDataRepository.save(temp);
                    });
            }

            results.stream()
                .forEach(r->{
                    votingResultsRepository.save(r);
                });



        }


    }
}
