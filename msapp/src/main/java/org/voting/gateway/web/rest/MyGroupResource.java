package org.voting.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.voting.gateway.domain.MyGroup;

import org.voting.gateway.repository.MyGroupRepository;
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
 * REST controller for managing MyGroup.
 */
@RestController
@RequestMapping("/api")
public class MyGroupResource {

    private final Logger log = LoggerFactory.getLogger(MyGroupResource.class);

    private static final String ENTITY_NAME = "myGroup";

    private final MyGroupRepository myGroupRepository;

    public MyGroupResource(MyGroupRepository myGroupRepository) {
        this.myGroupRepository = myGroupRepository;
    }

    /**
     * POST  /my-groups : Create a new myGroup.
     *
     * @param myGroup the myGroup to create
     * @return the ResponseEntity with status 201 (Created) and with body the new myGroup, or with status 400 (Bad Request) if the myGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/my-groups")
    @Timed
    public ResponseEntity<MyGroup> createMyGroup(@Valid @RequestBody MyGroup myGroup) throws URISyntaxException {
        log.debug("REST request to save MyGroup : {}", myGroup);
        if (myGroup.getId() != null) {
            throw new BadRequestAlertException("A new myGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MyGroup result = myGroupRepository.save(myGroup);
        return ResponseEntity.created(new URI("/api/my-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /my-groups : Updates an existing myGroup.
     *
     * @param myGroup the myGroup to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated myGroup,
     * or with status 400 (Bad Request) if the myGroup is not valid,
     * or with status 500 (Internal Server Error) if the myGroup couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/my-groups")
    @Timed
    public ResponseEntity<MyGroup> updateMyGroup(@Valid @RequestBody MyGroup myGroup) throws URISyntaxException {
        log.debug("REST request to update MyGroup : {}", myGroup);
        if (myGroup.getId() == null) {
            return createMyGroup(myGroup);
        }
        MyGroup result = myGroupRepository.save(myGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, myGroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /my-groups : get all the myGroups.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of myGroups in body
     */
    @GetMapping("/my-groups")
    @Timed
    public List<MyGroup> getAllMyGroups() {
        log.debug("REST request to get all MyGroups");
        return myGroupRepository.findAll();
        }

    /**
     * GET  /my-groups/:id : get the "id" myGroup.
     *
     * @param id the id of the myGroup to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the myGroup, or with status 404 (Not Found)
     */
    @GetMapping("/my-groups/{id}")
    @Timed
    public ResponseEntity<MyGroup> getMyGroup(@PathVariable Long id) {
        log.debug("REST request to get MyGroup : {}", id);
        MyGroup myGroup = myGroupRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(myGroup));
    }

    /**
     * DELETE  /my-groups/:id : delete the "id" myGroup.
     *
     * @param id the id of the myGroup to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/my-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteMyGroup(@PathVariable Long id) {
        log.debug("REST request to delete MyGroup : {}", id);
        myGroupRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
