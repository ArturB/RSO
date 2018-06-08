package org.voting.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.voting.gateway.repository.VotesDesignationPackRepository;
import org.voting.gateway.service.VotesDesignationPackDTO;
import org.voting.gateway.web.rest.util.HeaderUtil;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class VotesDesignationPackResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private static final String ENTITY_NAME = "votesDesignationPack";

    private final VotesDesignationPackRepository votesDesignationPackRepository;
    private final ElectoralPeriodsResource electoralPeriodsResource;

    public VotesDesignationPackResource(VotesDesignationPackRepository votesDesignationPackRepository, ElectoralPeriodsResource electoralPeriodsResource) {
        this.votesDesignationPackRepository = votesDesignationPackRepository;

        this.electoralPeriodsResource = electoralPeriodsResource;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_OKW_MEMBER')")
    @PostMapping("/votes_designation_pack")
    @Timed
    public ResponseEntity<Void> addVotes(@Valid @RequestBody VotesDesignationPackDTO votesPack) throws URISyntaxException {
        log.debug("REST request to save votes : {}", votesPack);
        electoralPeriodsResource.isInPeriod("MidRoundPeriod", "PostElectionPeriod" );
       /* if (user.getId() != null) {
            throw new BadRequestAlertException("A new myUser cannot already have an ID", ENTITY_NAME, "idexists");
        }*/

       votesDesignationPackRepository.add(votesPack);


        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, votesPack.getUser_id().toString()))
            .build();
    }



    @PreAuthorize("hasAnyAuthority('ROLE_OKW_MEMBER')")
    @PutMapping("/votes_designation_pack")
    @Timed
    public ResponseEntity<Void> updateVotes(@Valid @RequestBody VotesDesignationPackDTO votesPack) throws URISyntaxException {
        log.debug("REST request to update VotesDesignationPack : {}", votesPack);
        electoralPeriodsResource.isInPeriod("MidRoundPeriod", "PostElectionPeriod" );

        votesDesignationPackRepository.edit(votesPack);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, votesPack.getUser_id().toString()))
            .build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_OKW_MEMBER')")
    @GetMapping("/votes_designation_pack/getFromUser/{round}/{userId}")
    @Timed
    public List<VotesDesignationPackDTO> getVotes(@PathVariable Integer round, @PathVariable UUID userId) {
        log.debug("REST request to get  VotesDesignationPack : round {} userId {} ", round, userId);

        List<VotesDesignationPackDTO> votePack = votesDesignationPackRepository.findByTurnByUser(round,userId);

        return votePack;
    }
}
