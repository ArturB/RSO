package org.voting.gateway.repository;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.VotingData;
import org.voting.gateway.domain.VotingReport;
import org.voting.gateway.domain.VotingResults;
import org.voting.gateway.service.VotesDesignationPackDTO;
import org.voting.gateway.service.VotesDesignationSingleCandidateDTO;
import org.voting.gateway.service.VotesResultDTO;

import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;

@Repository
public class VotesSumRepository {

	private final VotingReportRepository votingReportRepository;
	private final VotingResultsRepository votingResultsRepository;

    public VotesSumRepository(VotingReportRepository votingReportRepository,VotingResultsRepository votingResultsRepository) {
        this.votingReportRepository = votingReportRepository;
        this.votingResultsRepository = votingResultsRepository;



    }



    public VotesResultDTO getAllVotesInDistrict(UUID districtId, UUID roundId)
    {
    	VotesResultDTO votesResults = new VotesResultDTO();

    	List<VotingReport> reports = votingReportRepository.findReportsByDistrictRound(districtId, roundId);
    	if(reports.isEmpty()) return null;

        votesResults.setNo_can_vote(reports.get(0).getNoCanVote());
        votesResults.setNr_cards_used(reports.get(0).getNoCardsUsed());

    	Map<String,List<VotingReport>> repMap = reports.stream()
            .collect(Collectors.groupingBy(VotingReport::getVoteType));


    	if(!repMap.containsKey("TOO MANY")) votesResults.setToo_many_marks_cards_used(0);
    	else  votesResults.setToo_many_marks_cards_used(repMap.get("TOO MANY").stream()
            .mapToInt(r-> r.getNoOfVotes()).sum());

        if(!repMap.containsKey("NONE")) votesResults.setNone_marks_cards_used(0);
        else  votesResults.setNone_marks_cards_used(repMap.get("NONE").stream()
            .mapToInt(r-> r.getNoOfVotes()).sum());

        if(!repMap.containsKey("ERASED")) votesResults.setErased_marks_cards_used(0);
        else  votesResults.setErased_marks_cards_used(repMap.get("ERASED").stream()
            .mapToInt(r-> r.getNoOfVotes()).sum());

        List<VotesDesignationSingleCandidateDTO> candidatesVotes = new LinkedList<VotesDesignationSingleCandidateDTO>();

        if(!repMap.containsKey("VALID")) votesResults.setCandidate_votes(candidatesVotes);
        else
        {
            Map<UUID,Integer> candidateVRs = repMap.get("VALID").stream()
                .collect(Collectors.groupingBy(VotingReport::getCandidateId, Collectors.summingInt(VotingReport::getNoOfVotes)));

            candidatesVotes = candidateVRs.keySet().stream()
                .map(v-> {
                    VotesDesignationSingleCandidateDTO voteSingle = new VotesDesignationSingleCandidateDTO();
                    voteSingle.setCandidate_id(v);
                    voteSingle.setNumber_of_votes(candidateVRs.get(v));
                    return voteSingle;
                }).collect(Collectors.toList());
        }

        votesResults.setCandidate_votes(candidatesVotes);

    	return votesResults;
    }

    public VotesResultDTO getAllVotesInMunicipality(UUID municipalityId, UUID round) {
        VotesResultDTO votesResults = new VotesResultDTO();
        List<VotingResults> results = votingResultsRepository.findByMunicipalityByRound( municipalityId, round);
        if(results.isEmpty()) return null;

        votesResults.setNo_can_vote(results.get(0).getNoCanVote());
        votesResults.setNr_cards_used(results.get(0).getNoCardsUsed());

        Map<String,List<VotingResults>> repMap = results.stream()
            .collect(Collectors.groupingBy(VotingResults::getVoteType));


        if(!repMap.containsKey("TOO MANY")) votesResults.setToo_many_marks_cards_used(0);
        else  votesResults.setToo_many_marks_cards_used(repMap.get("TOO MANY").stream()
            .mapToInt(r-> r.getNoOfVotes()).sum());

        if(!repMap.containsKey("NONE")) votesResults.setNone_marks_cards_used(0);
        else  votesResults.setNone_marks_cards_used(repMap.get("NONE").stream()
            .mapToInt(r-> r.getNoOfVotes()).sum());

        if(!repMap.containsKey("ERASED")) votesResults.setErased_marks_cards_used(0);
        else  votesResults.setErased_marks_cards_used(repMap.get("ERASED").stream()
            .mapToInt(r-> r.getNoOfVotes()).sum());

        List<VotesDesignationSingleCandidateDTO> candidatesVotes = new LinkedList<VotesDesignationSingleCandidateDTO>();

        if(!repMap.containsKey("VALID")) votesResults.setCandidate_votes(candidatesVotes);
        else
        {
            Map<UUID,Integer> candidateVRs = repMap.get("VALID").stream()
                .collect(Collectors.groupingBy(VotingResults::getCandidateId, Collectors.summingInt(VotingResults::getNoOfVotes)));

            candidatesVotes = candidateVRs.keySet().stream()
                .map(v-> {
                    VotesDesignationSingleCandidateDTO voteSingle = new VotesDesignationSingleCandidateDTO();
                    voteSingle.setCandidate_id(v);
                    voteSingle.setNumber_of_votes(candidateVRs.get(v));
                    return voteSingle;
                }).collect(Collectors.toList());
        }

        votesResults.setCandidate_votes(candidatesVotes);

        return votesResults;  
    }
}
