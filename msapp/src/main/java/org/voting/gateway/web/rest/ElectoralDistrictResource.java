package org.voting.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datastax.driver.core.utils.UUIDs;
import org.voting.gateway.domain.ElectoralDistrict;

import org.voting.gateway.repository.ElectoralDistrictRepository;

import org.voting.gateway.service.ElectoralDistrictDTO;
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
import java.util.UUID;
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
     * @param electoralDistrictDTO the electoralDistrict to create
     * @return the ResponseEntity with status 201 (Created) and with body the new electoralDistrict, or with status 400 (Bad Request) if the electoralDistrict has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/electoral-districts")
    @Timed
    public ResponseEntity<ElectoralDistrictDTO> createElectoralDistrict(@Valid @RequestBody ElectoralDistrictDTO electoralDistrictDTO) throws URISyntaxException {
        log.debug("REST request to save ElectoralDistrict : {}", electoralDistrictDTO);
        if (electoralDistrictDTO.getElectoral_district_id() != null) {
            throw new BadRequestAlertException("A new electoralDistrict cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ElectoralDistrict electoralDistrict = new ElectoralDistrict();
        electoralDistrict.setElectoral_district_id(UUIDs.timeBased());
        electoralDistrict.setMunicipalityId(electoralDistrictDTO.getMunicipality_id());
        electoralDistrict.setElectoralDistrictName(electoralDistrictDTO.getElectoral_district_name());
        electoralDistrictRepository.create(electoralDistrict);
        electoralDistrictDTO.setElectoral_district_id(electoralDistrict.getElectoral_district_id());
        return ResponseEntity.created(new URI("/api/electoral-districts/" + electoralDistrict.getElectoral_district_id()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, electoralDistrict.getElectoral_district_id().toString()))
            .body(electoralDistrictDTO);
    }

    /**
     * PUT  /electoral-districts : Updates an existing electoralDistrict.
     *
     * @param electoralDistrictDTO the electoralDistrict to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated electoralDistrict,
     * or with status 400 (Bad Request) if the electoralDistrict is not valid,
     * or with status 500 (Internal Server Error) if the electoralDistrict couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/electoral-districts")
    @Timed
    public ResponseEntity<ElectoralDistrictDTO> updateElectoralDistrict(@Valid @RequestBody ElectoralDistrictDTO electoralDistrictDTO) throws URISyntaxException {
        log.debug("REST request to update ElectoralDistrict : {}", electoralDistrictDTO);
        if (electoralDistrictDTO.getElectoral_district_id() == null) {
            return createElectoralDistrict(electoralDistrictDTO);
        }
        ElectoralDistrict electoralDistrict = new ElectoralDistrict();
        electoralDistrict.setElectoral_district_id(UUIDs.timeBased());
        electoralDistrict.setMunicipalityId(electoralDistrictDTO.getMunicipality_id());
        electoralDistrict.setElectoralDistrictName(electoralDistrictDTO.getElectoral_district_name());
        electoralDistrictRepository.save(electoralDistrict);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, electoralDistrict.getElectoral_district_id().toString()))
            .body(electoralDistrictDTO);
    }

    /**
     * GET  /electoral-districts : get all the electoralDistricts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of electoralDistricts in body
     */
    @GetMapping("/electoral-districts")
    @Timed
    public List<ElectoralDistrictDTO> getAllElectoralDistricts() {
        log.debug("REST request to get all ElectoralDistricts");
        return electoralDistrictRepository.findAll().stream().map(ElectoralDistrictDTO::new).collect(Collectors.toList());
    }

    /**
     * GET  /electoral-districts/:id : get the "id" electoralDistrict.
     *
     * @param id the id of the electoralDistrict to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the electoralDistrict, or with status 404 (Not Found)
     */
    @GetMapping("/electoral-districts/{id}")
    @Timed
    public ResponseEntity<ElectoralDistrictDTO> getElectoralDistrict(@PathVariable UUID id) {
        log.debug("REST request to get ElectoralDistrict : {}", id);
        ElectoralDistrictDTO electoralDistrict = electoralDistrictRepository.findOneDTO(id);
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
    public ResponseEntity<Void> deleteElectoralDistrict(@PathVariable UUID id) {
        log.debug("REST request to delete ElectoralDistrict : {}", id);
        electoralDistrictRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/municipalities/{municipalityId}/electoral_districts")
    @Timed
    public List<ElectoralDistrictDTO> getElectoralDistrictsByMunicipalityId(@PathVariable UUID municipalityId){
        return electoralDistrictRepository.findInMunicipalityDTO(municipalityId);
    }
}
