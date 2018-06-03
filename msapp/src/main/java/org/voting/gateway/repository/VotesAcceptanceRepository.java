package org.voting.gateway.repository;

import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.Candidate;

import com.datastax.driver.mapping.Mapper;

@Repository
public class VotesAcceptanceRepository {

	// TODO narazie to jest zaslepka by bylo cokolwiek konstruktor
    //private final CandidateRepository candidateRepository;
    private final VotesFromDistrictRepository votesFromDistrictRepository;
    
    public VotesAcceptanceRepository(VotesFromDistrictRepository votesFromDistrictRepository) {
    	        
        this.votesFromDistrictRepository = votesFromDistrictRepository;

    }
}
