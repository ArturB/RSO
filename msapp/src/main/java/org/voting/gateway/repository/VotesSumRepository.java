package org.voting.gateway.repository;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.VotingData;
import org.voting.gateway.domain.VotingReport;
import org.voting.gateway.service.VotesDesignationPackDTO;
import org.voting.gateway.service.VotesDesignationSingleCandidateDTO;
import org.voting.gateway.service.VotesResultDTO;

import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;

@Repository
public class VotesSumRepository {
	    
	private final VotingReportRepository votingReportRepository; 
	
    public VotesSumRepository(VotingReportRepository votingReportRepository) {
        this.votingReportRepository = votingReportRepository;
        
        
        
    }
    
    // TODO zrobic metode dajaca wszystkie glosy na podstawie district id i round
    
    public VotesResultDTO getAllVotesInDistrict(UUID districtId, UUID roundId)
    {
    	VotesResultDTO votesResults = new VotesResultDTO();
    	
    	List<VotingReport> reports = votingReportRepository.findReportsByDistrictRound(districtId, roundId);
    	List<VotesDesignationSingleCandidateDTO> candidates = new LinkedList<VotesDesignationSingleCandidateDTO>();
    	
    	int nr_cards_used = 0;
    	int tooManyMarksCardsUsed = 0;
    	int noneMarksCardsUsed = 0;
    	int erasedMarksCardsUsed = 0;
    	int no_can_vote = 0;
    	
    	List<VotesDesignationSingleCandidateDTO> votesForCandidates =
    			new LinkedList<VotesDesignationSingleCandidateDTO>();
    	
    	Map<UUID,Integer> candidatesVotes = new HashMap<UUID, Integer>();
    	
    	String type;
    	for (VotingReport vr : reports)
    	{
    		type = vr.getVoteType();
    		if (type.equals("Valid"))
    		{
    			
    		}
    		else if (type.equals("TOO MANY"))
    		{
    			
    		}
    		else if (type.equals("NONE"))
    		{
    			
    		}
    		else
    		{
    			
    		}
    		nr_cards_used += vr.getNoCardsUsed();
    		//tooManyMarksCardsUsed += vr.get
    		
    		//nr
    		
    		
    		
    	}
    	
    	for (UUID key : candidatesVotes.keySet() ) {
    		VotesDesignationSingleCandidateDTO votesForCandidate = new VotesDesignationSingleCandidateDTO();
    		votesForCandidate.setCandidate_id(key);
    		votesForCandidate.setNumber_of_votes(candidatesVotes.get(key));
    		candidates.add(votesForCandidate);
    	}
    	
    	votesResults.setCandidate_votes(candidates);
    	votesResults.setErased_marks_cards_used(erasedMarksCardsUsed);
    	votesResults.setNone_marks_cards_used(noneMarksCardsUsed);
    	votesResults.setToo_many_marks_cards_used(tooManyMarksCardsUsed);
    	votesResults.setNo_can_vote(no_can_vote);
    	votesResults.setNr_cards_used(nr_cards_used);
    	
    	return votesResults;
    }
}
