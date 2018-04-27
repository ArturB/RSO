package org.voting.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.voting.gateway.domain.VotesFromDistrict;

import org.voting.gateway.repository.VotesFromDistrictRepository;
import org.voting.gateway.web.rest.errors.BadRequestAlertException;
import org.voting.gateway.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing VotesFromDistrict.
 */
@RestController
@RequestMapping("/api")
public class VotesFromDistrictResource {

    private final Logger log = LoggerFactory.getLogger(VotesFromDistrictResource.class);

    private static final String ENTITY_NAME = "votesFromDistrict";

    private final VotesFromDistrictRepository votesFromDistrictRepository;

    public VotesFromDistrictResource(VotesFromDistrictRepository votesFromDistrictRepository) {
        this.votesFromDistrictRepository = votesFromDistrictRepository;
    }

    /**
     * POST  /votes-from-districts : Create a new votesFromDistrict.
     *
     * @param votesFromDistrict the votesFromDistrict to create
     * @return the ResponseEntity with status 201 (Created) and with body the new votesFromDistrict, or with status 400 (Bad Request) if the votesFromDistrict has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/votes-from-districts")
    @Timed
    public ResponseEntity<VotesFromDistrict> createVotesFromDistrict(@Valid @RequestBody VotesFromDistrict votesFromDistrict) throws URISyntaxException {
        log.debug("REST request to save VotesFromDistrict : {}", votesFromDistrict);
        if (votesFromDistrict.getId() != null) {
            throw new BadRequestAlertException("A new votesFromDistrict cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VotesFromDistrict result = votesFromDistrictRepository.save(votesFromDistrict);
        return ResponseEntity.created(new URI("/api/votes-from-districts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /votes-from-districts : Updates an existing votesFromDistrict.
     *
     * @param votesFromDistrict the votesFromDistrict to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated votesFromDistrict,
     * or with status 400 (Bad Request) if the votesFromDistrict is not valid,
     * or with status 500 (Internal Server Error) if the votesFromDistrict couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/votes-from-districts")
    @Timed
    public ResponseEntity<VotesFromDistrict> updateVotesFromDistrict(@Valid @RequestBody VotesFromDistrict votesFromDistrict) throws URISyntaxException {
        log.debug("REST request to update VotesFromDistrict : {}", votesFromDistrict);
        if (votesFromDistrict.getId() == null) {
            return createVotesFromDistrict(votesFromDistrict);
        }
        VotesFromDistrict result = votesFromDistrictRepository.save(votesFromDistrict);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, votesFromDistrict.getId().toString()))
            .body(result);
    }

    /**
     * GET  /votes-from-districts : get all the votesFromDistricts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of votesFromDistricts in body
     */
    @GetMapping("/votes-from-districts")
    @Timed
    public List<VotesFromDistrict> getAllVotesFromDistricts() {
        log.debug("REST request to get all VotesFromDistricts");
        return votesFromDistrictRepository.findAll();
        }

    /**
     * GET  /votes-from-districts/:id : get the "id" votesFromDistrict.
     *
     * @param id the id of the votesFromDistrict to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the votesFromDistrict, or with status 404 (Not Found)
     */
    @GetMapping("/votes-from-districts/{id}")
    @Timed
    public ResponseEntity<VotesFromDistrict> getVotesFromDistrict(@PathVariable Long id) {
        log.debug("REST request to get VotesFromDistrict : {}", id);
        VotesFromDistrict votesFromDistrict = votesFromDistrictRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(votesFromDistrict));
    }

    /**
     * DELETE  /votes-from-districts/:id : delete the "id" votesFromDistrict.
     *
     * @param id the id of the votesFromDistrict to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/votes-from-districts/{id}")
    @Timed
    public ResponseEntity<Void> deleteVotesFromDistrict(@PathVariable Long id) {
        log.debug("REST request to delete VotesFromDistrict : {}", id);
        votesFromDistrictRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
