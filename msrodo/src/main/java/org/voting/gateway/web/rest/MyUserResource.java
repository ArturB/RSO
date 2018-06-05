package org.voting.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.voting.gateway.domain.MyUser;

import org.voting.gateway.domain.SmallUser;
import org.voting.gateway.repository.MyUserRepository;
import org.voting.gateway.security.SecurityUtils;
import org.voting.gateway.web.rest.errors.BadRequestAlertException;
import org.voting.gateway.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.voting.gateway.web.rest.util.PaginationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * REST controller for managing MyUser.
 */
@RestController
@RequestMapping("/api")
public class MyUserResource {

    private final Logger log = LoggerFactory.getLogger(MyUserResource.class);

    private static final String ENTITY_NAME = "myUser";

    private final MyUserRepository myUserRepository;

    public MyUserResource(MyUserRepository myUserRepository) {
        this.myUserRepository = myUserRepository;
    }


    @GetMapping("/account")
    @Timed
    public ResponseEntity<SmallUser> getAccount(@RequestHeader HttpHeaders headers) {
        log.debug("REST request to get account");
        Optional<SmallUser> myUser = SecurityUtils.getCurrentUserLogin()
            .flatMap(c -> {
                log.debug("T!#231: "+c);
                return myUserRepository.findByUsername(c).stream().findFirst();
            })
            .flatMap(c -> Optional.ofNullable(ToSmallUser(c)));
        return ResponseUtil.wrapOrNotFound(myUser);
    }

    @PostMapping(path = "/account/change-password")
    @Timed
    public void changePassword(@RequestBody String password) {
        throw new RuntimeException("TODO NOT IMPLEMENTED!!!");
    }

    /**
     * POST  /users : Create a new myUser.
     *
     * @param myUser the myUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new myUser, or with status 400 (Bad Request) if the myUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/users")
    @Timed
    public ResponseEntity<MyUser> createMyUser(@Valid @RequestBody MyUser myUser) throws URISyntaxException {
        log.debug("REST request to save MyUser : {}", myUser);
        if (myUser.getId() != null) {
            throw new BadRequestAlertException("A new myUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MyUser result = myUserRepository.save(myUser);
        return ResponseEntity.created(new URI("/api/users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /users : Updates an existing myUser.
     *
     * @param myUser the myUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated myUser,
     * or with status 400 (Bad Request) if the myUser is not valid,
     * or with status 500 (Internal Server Error) if the myUser couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/users")
    @Timed
    public ResponseEntity<MyUser> updateMyUser(@Valid @RequestBody MyUser myUser) throws URISyntaxException {
        log.debug("REST request to update MyUser : {}", myUser);
        if (myUser.getId() == null) {
            return createMyUser(myUser);
        }
        MyUser result = myUserRepository.save(myUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, myUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /users : get all the myUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of myUsers in body
     */
    @GetMapping("/users")
    @Timed
    public ResponseEntity<List<MyUser>> getAllMyUsers(Pageable pageable) {
        log.debug("REST request to get all MyUsers");
        Page<MyUser> page = myUserRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /users/:id : get the "id" myUser.
     *
     * @param id the id of the myUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the myUser, or with status 404 (Not Found)
     */
    @GetMapping("/users/{id}")
    @Timed
    public ResponseEntity<MyUser> getMyUser(@PathVariable Long id) {
        log.debug("REST request to get MyUser : {}", id);
        MyUser myUser = myUserRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(myUser));
    }

    /**
     * DELETE  /users/:id : delete the "id" myUser.
     *
     * @param id the id of the myUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/{id}")
    @Timed
    public ResponseEntity<Void> deleteMyUser(@PathVariable Long id) {
        log.debug("REST request to delete MyUser : {}", id);
        myUserRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/users/{id}/disable")
    @Timed
    public ResponseEntity<Void> disableMyUser(@PathVariable Long id) {
        log.debug("REST request to disable MyUser : {}", id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("Zablokowano konto użytkownika o id "+id, id.toString()))
            .build();
    }

    @GetMapping("/municipalities/{municipalityId}/users")
    @Timed
    public List<SmallUser> getSmallUserByMunicipalityId(@PathVariable Long municipalityId) {
        log.debug("REST request to get SmallUser by municipalityId : {}", municipalityId);
        return myUserRepository.findAll()
            .stream()
            .filter(c -> c.getMunicipality() != null)
            .filter(c -> c.getMunicipality().getId().equals(municipalityId))
            .map(this::ToSmallUser).collect(Collectors.toList());
    }

    @GetMapping("/districts/{districtId}/users")
    @Timed
    public List<SmallUser> getSmallUserByDistrictId(@PathVariable Long districtId) {
        log.debug("REST request to get SmallUser by districtId: {}", districtId);
        return myUserRepository.findAll()
            .stream()
            .filter(c -> c.getMunicipality() != null)
            .filter(c -> c.getElectoralDistrict() != null)
            .filter(c -> c.getElectoralDistrict().getId().equals(districtId))
            .map(this::ToSmallUser).collect(Collectors.toList());
    }

    @GetMapping("/custom-users/{id}")
    @Timed
    public ResponseEntity<SmallUser> getSmallUser(@PathVariable Long id) {
        log.debug("REST request to get  SmallUser: {}", id);
        MyUser myUser = myUserRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ToSmallUser(myUser)));
    }

    @DeleteMapping("/custom-users/{id}")
    @Timed
    public ResponseEntity<SmallUser> deleteSmallUser(@PathVariable Long id) {
        log.debug("REST request to delete SmallUser: {}", id);
        myUserRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    private SmallUser ToSmallUser(MyUser user) {
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
    }

}
