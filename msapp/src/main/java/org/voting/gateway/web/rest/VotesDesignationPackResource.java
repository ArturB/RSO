package org.voting.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datastax.driver.core.utils.UUIDs;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.voting.gateway.repository.VotesDesignationPackRepository;
import org.voting.gateway.service.VotesDesignationPackDTO;
import org.voting.gateway.web.rest.util.HeaderUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class VotesDesignationPackResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private static final String ENTITY_NAME = "votesDesignationPack";



    private final VotesDesignationPackRepository votesDesignationPackRepository;

    public VotesDesignationPackResource(VotesDesignationPackRepository votesDesignationPackRepository) {
        this.votesDesignationPackRepository = votesDesignationPackRepository;

    }


    @PostMapping("/votes_designation_pack")
    @Timed
    public ResponseEntity<Void> addVotes(@Valid @RequestBody VotesDesignationPackDTO votesPack) throws URISyntaxException {
        log.debug("REST request to save votes : {}", votesPack);
       /* if (user.getId() != null) {
            throw new BadRequestAlertException("A new myUser cannot already have an ID", ENTITY_NAME, "idexists");
        }*/

       votesDesignationPackRepository.add(votesPack);


        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, votesPack.getUserId().toString()))
            .build();
    }



    @PutMapping("/votes_designation_pack")
    @Timed
    public ResponseEntity<Void> updateVotes(@Valid @RequestBody VotesDesignationPackDTO votesPack) throws URISyntaxException {
        log.debug("REST request to update VotesDesignationPack : {}", votesPack);

        votesDesignationPackRepository.edit(votesPack);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, votesPack.getUserId().toString()))
            .build();
    }

    @GetMapping("/votes_designation_pack/getFromUser/{round}/{userId}")
    @Timed
    public ResponseEntity<VotesDesignationPackDTO> getVotes(@PathVariable UUID turnId, @PathVariable UUID userId) {
        log.debug("REST request to get  VotesDesignationPack : round {} userId {} ", turnId, userId);

        VotesDesignationPackDTO votePack = votesDesignationPackRepository.findOneByTurnByUser(turnId,userId);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(votePack));
    }

}
