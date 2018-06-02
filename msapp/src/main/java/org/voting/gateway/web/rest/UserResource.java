package org.voting.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datastax.driver.core.utils.UUIDs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.voting.gateway.domain.MyUser;

import org.voting.gateway.domain.SmallUser;
import org.voting.gateway.repository.SmallUserRepository;
import org.voting.gateway.security.SecurityUtils;
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

import java.util.*;
import java.util.stream.Collectors;

/**
 * REST controller for managing MyUser.
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private static final String ENTITY_NAME = "user";

    private final SmallUserRepository smallUserRepository;

    public UserResource(SmallUserRepository smallUserRepository) {
        this.smallUserRepository = smallUserRepository;
    }


    @GetMapping("/account")
    @Timed
    public ResponseEntity<SmallUser> getAccount() {
        log.debug("REST request to get account");
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        Optional<SmallUser> user = Optional.empty();
        if(login.isPresent())
        {
             List<SmallUser> users = smallUserRepository.findByUsername(login.get());
             if(!users.isEmpty())
             {
                 user = Optional.of(users.get(0));
             }
        }
        return ResponseUtil.wrapOrNotFound(user);
    }

    /**
     * POST  /my-users : Create a new myUser.
     *
     * @param user the myUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new myUser, or with status 400 (Bad Request) if the myUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/users")
    @Timed
    public ResponseEntity<SmallUser> createMyUser(@Valid @RequestBody SmallUser user) throws URISyntaxException {
        log.debug("REST request to save MyUser : {}", user);
        if (user.getId() != null) {
            throw new BadRequestAlertException("A new myUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        user.setId(UUIDs.timeBased());
        SmallUser result = smallUserRepository.save(user);
        return ResponseEntity.created(new URI("/api/my-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /my-users : Updates an existing myUser.
     *
     * @param user the myUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated myUser,
     * or with status 400 (Bad Request) if the myUser is not valid,
     * or with status 500 (Internal Server Error) if the myUser couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/users")
    @Timed
    public ResponseEntity<SmallUser> updateMyUser(@Valid @RequestBody SmallUser user) throws URISyntaxException {
        log.debug("REST request to update MyUser : {}", user);
        if (user.getId() == null) {
            return createMyUser(user);
        }
        SmallUser result = smallUserRepository.save(user);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, user.getId().toString()))
            .body(result);
    }

    /**
     * GET  /users : get all the Users.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of myUsers in body
     */
    @GetMapping("/users")
    @Timed
    public Page<SmallUser> getAllUsers(Pageable pageRequest) {
        log.debug("REST request to get all MyUsers");
        return smallUserRepository.findAllPaged(pageRequest);
        }

    /**
     * GET  /my-users/:id : get the "id" myUser.
     *
     * @param id the id of the myUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the myUser, or with status 404 (Not Found)
     */
    @GetMapping("/users/{id}")
    @Timed
    public ResponseEntity<SmallUser> getMyUser(@PathVariable UUID id) {
        log.debug("REST request to get MyUser : {}", id);
        SmallUser user = smallUserRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(user));
    }

    /**
     * DELETE  /my-users/:id : delete the "id" myUser.
     *
     * @param id the id of the myUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/{id}")
    @Timed
    public ResponseEntity<Void> deleteMyUser(@PathVariable UUID id) {
        log.debug("REST request to delete MyUser : {}", id);
        smallUserRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    @GetMapping("/municipalities/{municipalityId}/users")
    @Timed
    public List<SmallUser> getSmallUserByMunicipalityId(@PathVariable UUID municipalityId) {
        log.debug("REST request to get SmallUser by municipalityId : {}", municipalityId);
        return smallUserRepository.findInMunicipality(municipalityId);

    }

    @GetMapping("/districts/{districtId}/users")
    @Timed
    public List<SmallUser> getSmallUserByDistrictId(@PathVariable UUID districtId) {
        log.debug("REST request to get SmallUser by districtId: {}", districtId);
        return smallUserRepository.findInDistrict(districtId);

    }

    @GetMapping("/users/small/{id}")
    @Timed
    public ResponseEntity<SmallUser> getSmallUser(@PathVariable UUID id) {
        return getMyUser(id);
    }

    /*private SmallUser ToSmallUser(MyUser user) {
        SmallUser smallUser = new SmallUser();
        smallUser.setId(user.getId());
        if(user.getElectoralDistrict() != null ) {
            smallUser.setElectoralDistrictId(user.getElectoralDistrict().getId());
        }
        if(user.getMunicipality() != null){
            smallUser.setMunicipalityId(user.getMunicipality().getId());
        }
        smallUser.setRole(user.getRole());
        smallUser.setUsername(user.getUsername());
        HashSet<String>  authorities = new HashSet<>(Arrays.asList(smallUser.getRole(), "ROLE_USER"));
        if(smallUser.getRole().equals("ROLE_GKW_LEADER")){
            authorities.add("ROLE_GKW_MEMBER");
        }
        if(smallUser.getRole().equals("ROLE_OKW_LEADER")){
            authorities.add("ROLE_OKW_MEMBER");
        }

        smallUser.setAuthorities(authorities);
        return smallUser;
    }*/
}
