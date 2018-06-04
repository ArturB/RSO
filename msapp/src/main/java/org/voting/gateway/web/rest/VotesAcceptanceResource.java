package org.voting.gateway.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.voting.gateway.repository.CandidateRepository;
import org.voting.gateway.repository.VotesAcceptanceRepository;

import com.codahale.metrics.annotation.Timed;

@RestController
@RequestMapping("/api")
public class VotesAcceptanceResource {

    private final Logger log = LoggerFactory.getLogger(VotesAcceptanceResource.class);

    private static final String ENTITY_NAME = "votesAcceptance";

    private final VotesAcceptanceRepository votesAcceptanceRepository;
    
    public VotesAcceptanceResource(VotesAcceptanceRepository votesAcceptanceRepository) {
        this.votesAcceptanceRepository = votesAcceptanceRepository;

    }
    
    // TODO Dodac metody 2
    
    @PostMapping("/districts/{districtId}/{round}/acceptVotes")
    @Timed
    public void acceptVotes(@PathVariable long districtId, @PathVariable long round){
        log.debug("REST request to accept votes from district: {} and round {}", districtId, round);
    }
}
