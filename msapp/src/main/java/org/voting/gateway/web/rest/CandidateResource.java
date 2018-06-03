package org.voting.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.voting.gateway.domain.Candidate;

import org.voting.gateway.repository.CandidateRepository;
import org.voting.gateway.web.rest.errors.BadRequestAlertException;
import org.voting.gateway.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.datastax.driver.core.utils.UUIDs;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * REST controller for managing Candidate.
 */
@RestController
@RequestMapping("/api")
public class CandidateResource {

    private final Logger log = LoggerFactory.getLogger(CandidateResource.class);

    private static final String ENTITY_NAME = "candidate";

    private final CandidateRepository candidateRepository;

    public CandidateResource(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;

    }

    /**
     * POST  /candidates : Create a new candidate.
     *
     * @param candidate the candidate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new candidate, or with status 400 (Bad Request) if the candidate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/candidates")
    @Timed
    public ResponseEntity<Candidate> createCandidate(@Valid @RequestBody Candidate candidate) throws URISyntaxException {
        log.debug("REST request to save Candidate : {}", candidate);
        if (candidate.getId() != null) {
            throw new BadRequestAlertException("A new candidate cannot already have an ID", ENTITY_NAME, "idexists");
        }

        candidate.setId(UUIDs.timeBased());
        Candidate result = candidateRepository.save(candidate);
        return ResponseEntity.created(new URI("/api/candidates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /candidates : Updates an existing candidate.
     *
     * @param candidate the candidate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated candidate,
     * or with status 400 (Bad Request) if the candidate is not valid,
     * or with status 500 (Internal Server Error) if the candidate couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/candidates")
    @Timed
    public ResponseEntity<Candidate> updateCandidate(@Valid @RequestBody Candidate candidate) throws URISyntaxException {
        log.debug("REST request to update Candidate : {}", candidate);
        if (candidate.getId() == null) {
            return createCandidate(candidate);
        }
        Candidate result = candidateRepository.save(candidate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, candidate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /candidates : get all the candidates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of candidates in body
     */
    @GetMapping("/candidates")
    @Timed
    public Page<Candidate> getAllCandidates(Pageable pageRequest) {
        log.debug("REST request to get all Candidates");
    	return candidateRepository.findAllPaged(pageRequest);
    }
    //PageImpl<Candidate>
    /**
     * GET  /candidates/:id : get the "id" candidate.
     *
     * @param id the id of the candidate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the candidate, or with status 404 (Not Found)
     */
    @GetMapping("/candidates/{id}")
    @Timed
    public ResponseEntity<Candidate> getCandidate(@PathVariable UUID id) {
        log.debug("REST request to get Candidate : {}", id);
        Candidate candidate = candidateRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(candidate));
    }

    /**
     * DELETE  /candidates/:id : delete the "id" candidate.
     *
     * @param id the id of the candidate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/candidates/{id}")
    @Timed
    public ResponseEntity<Void> deleteCandidate(@PathVariable UUID id) {
        log.debug("REST request to delete Candidate : {}", id);
        candidateRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET 
     * @param municipalityId the id of the municipalityId
     * @return the list of candidates
     */
    @GetMapping("/municipalities/{municipalityId}/candidates")
    @Timed
    public List<Candidate> getElectoralDistrictsByMunicipalityId(@PathVariable UUID municipalityId){
        return candidateRepository.findInMunicipality(municipalityId);
    }

    /**
     * 
     * @param municipalityId id of municipality
     * @param turn a given turn
     * @return a list of candidates from muncipality
     */
    @GetMapping("/municipalities/{municipalityId}/{turn}/candidates")
    @Timed
    public List<Candidate> getElectoralDistrictsByMunicipalityId(@PathVariable UUID municipalityId, @PathVariable UUID turn){
        return candidateRepository.findInMunicipalityByTurn(municipalityId,turn);
    }
}
