package org.voting.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.voting.gateway.domain.Party;

import org.voting.gateway.repository.PartyRepository;
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
 * REST controller for managing Party.
 */
@RestController
@RequestMapping("/api")
public class PartyResource {

    private final Logger log = LoggerFactory.getLogger(PartyResource.class);

    private static final String ENTITY_NAME = "party";

    private final PartyRepository partyRepository;

    public PartyResource(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }

    /**
     * POST  /parties : Create a new party.
     *
     * @param party the party to create
     * @return the ResponseEntity with status 201 (Created) and with body the new party, or with status 400 (Bad Request) if the party has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/parties")
    @Timed
    public ResponseEntity<Party> createParty(@Valid @RequestBody Party party) throws URISyntaxException {
        log.debug("REST request to save Party : {}", party);
        if (party.getId() != null) {
            throw new BadRequestAlertException("A new party cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Party result = partyRepository.save(party);
        return ResponseEntity.created(new URI("/api/parties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /parties : Updates an existing party.
     *
     * @param party the party to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated party,
     * or with status 400 (Bad Request) if the party is not valid,
     * or with status 500 (Internal Server Error) if the party couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/parties")
    @Timed
    public ResponseEntity<Party> updateParty(@Valid @RequestBody Party party) throws URISyntaxException {
        log.debug("REST request to update Party : {}", party);
        if (party.getId() == null) {
            return createParty(party);
        }
        Party result = partyRepository.save(party);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, party.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parties : get all the parties.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of parties in body
     */
    @GetMapping("/parties")
    @Timed
    public List<Party> getAllParties() {
        log.debug("REST request to get all Parties");
        return partyRepository.findAll();
        }

    /**
     * GET  /parties/:id : get the "id" party.
     *
     * @param id the id of the party to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the party, or with status 404 (Not Found)
     */
    @GetMapping("/parties/{id}")
    @Timed
    public ResponseEntity<Party> getParty(@PathVariable Long id) {
        log.debug("REST request to get Party : {}", id);
        Party party = partyRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(party));
    }

    /**
     * DELETE  /parties/:id : delete the "id" party.
     *
     * @param id the id of the party to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/parties/{id}")
    @Timed
    public ResponseEntity<Void> deleteParty(@PathVariable Long id) {
        log.debug("REST request to delete Party : {}", id);
        partyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
