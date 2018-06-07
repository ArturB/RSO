package org.voting.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.voting.gateway.domain.DuxxEntity;

import org.voting.gateway.repository.DuxxEntityRepository;
import org.voting.gateway.web.rest.errors.BadRequestAlertException;
import org.voting.gateway.web.rest.util.HeaderUtil;
import org.voting.gateway.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DuxxEntity.
 */
@RestController
@RequestMapping("/api")
public class DuxxEntityResource {

    private final Logger log = LoggerFactory.getLogger(DuxxEntityResource.class);

    private static final String ENTITY_NAME = "duxxEntity";

    private final DuxxEntityRepository duxxEntityRepository;

    public DuxxEntityResource(DuxxEntityRepository duxxEntityRepository) {
        this.duxxEntityRepository = duxxEntityRepository;
    }

    /**
     * POST  /duxx-entities : Create a new duxxEntity.
     *
     * @param duxxEntity the duxxEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new duxxEntity, or with status 400 (Bad Request) if the duxxEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/duxx-entities")
    @Timed
    public ResponseEntity<DuxxEntity> createDuxxEntity(@RequestBody DuxxEntity duxxEntity) throws URISyntaxException {
        log.debug("REST request to save DuxxEntity : {}", duxxEntity);
        if (duxxEntity.getId() != null) {
            throw new BadRequestAlertException("A new duxxEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DuxxEntity result = duxxEntityRepository.save(duxxEntity);
        return ResponseEntity.created(new URI("/api/duxx-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /duxx-entities : Updates an existing duxxEntity.
     *
     * @param duxxEntity the duxxEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated duxxEntity,
     * or with status 400 (Bad Request) if the duxxEntity is not valid,
     * or with status 500 (Internal Server Error) if the duxxEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/duxx-entities")
    @Timed
    public ResponseEntity<DuxxEntity> updateDuxxEntity(@RequestBody DuxxEntity duxxEntity) throws URISyntaxException {
        log.debug("REST request to update DuxxEntity : {}", duxxEntity);
        if (duxxEntity.getId() == null) {
            return createDuxxEntity(duxxEntity);
        }
        DuxxEntity result = duxxEntityRepository.save(duxxEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, duxxEntity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /duxx-entities : get all the duxxEntities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of duxxEntities in body
     */
    @GetMapping("/duxx-entities")
    @Timed
    public ResponseEntity<List<DuxxEntity>> getAllDuxxEntities(Pageable pageable) {
        log.debug("REST request to get a page of DuxxEntities");
        Page<DuxxEntity> page = duxxEntityRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/duxx-entities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /duxx-entities/:id : get the "id" duxxEntity.
     *
     * @param id the id of the duxxEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the duxxEntity, or with status 404 (Not Found)
     */
    @GetMapping("/duxx-entities/{id}")
    @Timed
    public ResponseEntity<DuxxEntity> getDuxxEntity(@PathVariable Long id) {
        log.debug("REST request to get DuxxEntity : {}", id);
        DuxxEntity duxxEntity = duxxEntityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(duxxEntity));
    }

    /**
     * DELETE  /duxx-entities/:id : delete the "id" duxxEntity.
     *
     * @param id the id of the duxxEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/duxx-entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteDuxxEntity(@PathVariable Long id) {
        log.debug("REST request to delete DuxxEntity : {}", id);
        duxxEntityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
