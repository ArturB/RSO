package org.voting.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.voting.gateway.domain.Municipality;

import org.voting.gateway.repository.MunicipalityRepository;
import org.voting.gateway.web.rest.errors.BadRequestAlertException;
import org.voting.gateway.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Municipality.
 */
@RestController
@RequestMapping("/api")
public class MunicipalityResource {

    private final Logger log = LoggerFactory.getLogger(MunicipalityResource.class);

    private static final String ENTITY_NAME = "municipality";

    private final MunicipalityRepository municipalityRepository;

    public MunicipalityResource(MunicipalityRepository municipalityRepository) {
        this.municipalityRepository = municipalityRepository;
    }

    /**
     * POST  /municipalities : Create a new municipality.
     *
     * @param municipality the municipality to create
     * @return the ResponseEntity with status 201 (Created) and with body the new municipality, or with status 400 (Bad Request) if the municipality has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/municipalities")
    @Timed
    public ResponseEntity<Municipality> createMunicipality(@RequestBody Municipality municipality) throws URISyntaxException {
        log.debug("REST request to save Municipality : {}", municipality);
        if (municipality.getId() != null) {
            throw new BadRequestAlertException("A new municipality cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Municipality result = municipalityRepository.save(municipality);
        return ResponseEntity.created(new URI("/api/municipalities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /municipalities : Updates an existing municipality.
     *
     * @param municipality the municipality to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated municipality,
     * or with status 400 (Bad Request) if the municipality is not valid,
     * or with status 500 (Internal Server Error) if the municipality couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/municipalities")
    @Timed
    public ResponseEntity<Municipality> updateMunicipality(@RequestBody Municipality municipality) throws URISyntaxException {
        log.debug("REST request to update Municipality : {}", municipality);
        if (municipality.getId() == null) {
            return createMunicipality(municipality);
        }
        Municipality result = municipalityRepository.save(municipality);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, municipality.getId().toString()))
            .body(result);
    }

    /**
     * GET  /municipalities : get all the municipalities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of municipalities in body
     */
    @GetMapping("/municipalities")
    @Timed
    public List<Municipality> getAllMunicipalities() {
        log.debug("REST request to get all Municipalities");
        return municipalityRepository.findAll();
        }

    /**
     * GET  /municipalities/:id : get the "id" municipality.
     *
     * @param id the id of the municipality to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the municipality, or with status 404 (Not Found)
     */
    @GetMapping("/municipalities/{id}")
    @Timed
    public ResponseEntity<Municipality> getMunicipality(@PathVariable Long id) {
        log.debug("REST request to get Municipality : {}", id);
        Municipality municipality = municipalityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(municipality));
    }

    /**
     * DELETE  /municipalities/:id : delete the "id" municipality.
     *
     * @param id the id of the municipality to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/municipalities/{id}")
    @Timed
    public ResponseEntity<Void> deleteMunicipality(@PathVariable Long id) {
        log.debug("REST request to delete Municipality : {}", id);
        municipalityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
