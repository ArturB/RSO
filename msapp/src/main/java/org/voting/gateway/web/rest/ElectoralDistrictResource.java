package org.voting.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.voting.gateway.domain.ElectoralDistrict;

import org.voting.gateway.repository.ElectoralDistrictRepository;
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
import java.util.stream.Collectors;

/**
 * REST controller for managing ElectoralDistrict.
 */
@RestController
@RequestMapping("/api")
public class ElectoralDistrictResource {

    private final Logger log = LoggerFactory.getLogger(ElectoralDistrictResource.class);

    private static final String ENTITY_NAME = "electoralDistrict";

    private final ElectoralDistrictRepository electoralDistrictRepository;

    public ElectoralDistrictResource(ElectoralDistrictRepository electoralDistrictRepository) {
        this.electoralDistrictRepository = electoralDistrictRepository;
    }

    /**
     * POST  /electoral-districts : Create a new electoralDistrict.
     *
     * @param electoralDistrict the electoralDistrict to create
     * @return the ResponseEntity with status 201 (Created) and with body the new electoralDistrict, or with status 400 (Bad Request) if the electoralDistrict has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/electoral-districts")
    @Timed
    public ResponseEntity<ElectoralDistrict> createElectoralDistrict(@Valid @RequestBody ElectoralDistrict electoralDistrict) throws URISyntaxException {
        log.debug("REST request to save ElectoralDistrict : {}", electoralDistrict);
        if (electoralDistrict.getId() != null) {
            throw new BadRequestAlertException("A new electoralDistrict cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ElectoralDistrict result = electoralDistrictRepository.save(electoralDistrict);
        return ResponseEntity.created(new URI("/api/electoral-districts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /electoral-districts : Updates an existing electoralDistrict.
     *
     * @param electoralDistrict the electoralDistrict to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated electoralDistrict,
     * or with status 400 (Bad Request) if the electoralDistrict is not valid,
     * or with status 500 (Internal Server Error) if the electoralDistrict couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/electoral-districts")
    @Timed
    public ResponseEntity<ElectoralDistrict> updateElectoralDistrict(@Valid @RequestBody ElectoralDistrict electoralDistrict) throws URISyntaxException {
        log.debug("REST request to update ElectoralDistrict : {}", electoralDistrict);
        if (electoralDistrict.getId() == null) {
            return createElectoralDistrict(electoralDistrict);
        }
        ElectoralDistrict result = electoralDistrictRepository.save(electoralDistrict);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, electoralDistrict.getId().toString()))
            .body(result);
    }

    /**
     * GET  /electoral-districts : get all the electoralDistricts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of electoralDistricts in body
     */
    @GetMapping("/electoral-districts")
    @Timed
    public List<ElectoralDistrict> getAllElectoralDistricts() {
        log.debug("REST request to get all ElectoralDistricts");
        return electoralDistrictRepository.findAll();
    }

    /**
     * GET  /electoral-districts/:id : get the "id" electoralDistrict.
     *
     * @param id the id of the electoralDistrict to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the electoralDistrict, or with status 404 (Not Found)
     */
    @GetMapping("/electoral-districts/{id}")
    @Timed
    public ResponseEntity<ElectoralDistrict> getElectoralDistrict(@PathVariable Long id) {
        log.debug("REST request to get ElectoralDistrict : {}", id);
        ElectoralDistrict electoralDistrict = electoralDistrictRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(electoralDistrict));
    }

    /**
     * DELETE  /electoral-districts/:id : delete the "id" electoralDistrict.
     *
     * @param id the id of the electoralDistrict to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/electoral-districts/{id}")
    @Timed
    public ResponseEntity<Void> deleteElectoralDistrict(@PathVariable Long id) {
        log.debug("REST request to delete ElectoralDistrict : {}", id);
        electoralDistrictRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/municipalities/{municipalityId}/electoral_districts")
    @Timed
    public List<ElectoralDistrict> getElectoralDistrictsByMunicipalityId(@PathVariable Long municipalityId){
        return electoralDistrictRepository.findAll()
            .stream().filter(c -> c.getMunicipality().getId().equals(municipalityId)).collect(Collectors.toList());
    }
}
