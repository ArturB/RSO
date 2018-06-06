package org.voting.gateway.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.voting.gateway.repository.CandidateRepository;
import org.voting.gateway.repository.VotesAcceptanceRepository;

import com.codahale.metrics.annotation.Timed;
import org.voting.gateway.service.VotesAcceptBodyDTO;
import org.voting.gateway.web.rest.util.HeaderUtil;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class VotesAcceptanceResource {

    private final Logger log = LoggerFactory.getLogger(VotesAcceptanceResource.class);

    private static final String ENTITY_NAME = "votesAcceptance";

    private final VotesAcceptanceRepository votesAcceptanceRepository;

    public VotesAcceptanceResource(VotesAcceptanceRepository votesAcceptanceRepository) {
        this.votesAcceptanceRepository = votesAcceptanceRepository;

    }



    @PostMapping("/districts/{districtId}/{round}/acceptVotes")
    @Timed
    public ResponseEntity<Void> acceptVotesDistrict(@PathVariable UUID districtId, @PathVariable UUID round, @Valid @RequestBody VotesAcceptBodyDTO votesAcceptBody){
        log.debug("REST request to accept votes from district: {} and round {}", districtId, round);

        votesAcceptanceRepository.acceptDistrict(districtId,round,votesAcceptBody);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createAlert("Zaakceptowano głosy",""))
            .build();
    }

    @PostMapping("/municipalities/{municipalityId}/{round}/acceptVotes")
    @Timed
    public ResponseEntity<Void> acceptVotesMunicipality(@PathVariable UUID municipalityId, @PathVariable UUID round){
        log.debug("REST request to accept votes from municipality: {} and round {}", municipalityId, round);

        votesAcceptanceRepository.acceptMunicipality(municipalityId,round);


        return ResponseEntity.ok()
            .headers(HeaderUtil.createAlert("Zaakceptowano głosy",""))
            .build();
    }
}
