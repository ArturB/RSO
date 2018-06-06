package org.voting.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;

import com.datastax.driver.core.utils.UUIDs;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.voting.gateway.domain.Candidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import org.voting.gateway.domain.ElectoralPeriod;
import org.voting.gateway.domain.MyUser;
import org.voting.gateway.domain.RodoUser;
import org.voting.gateway.service.SmallUserDTO;
import org.voting.gateway.service.UserDTO;
import org.voting.gateway.domain.SmallUser;
import org.voting.gateway.repository.SmallUserRepository;
import org.voting.gateway.security.SecurityUtils;
import org.voting.gateway.service.LoginDataDTO;
import org.voting.gateway.service.RodoUserDTO;
import org.voting.gateway.web.rest.errors.BadRequestAlertException;
import org.voting.gateway.web.rest.errors.InvalidPasswordException;
import org.voting.gateway.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.*;
import org.voting.gateway.web.rest.util.PaginationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;


import java.time.temporal.ChronoUnit;
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

    @Autowired
    @Lazy
    @Qualifier("loadBalancedRestTemplate")
    RestTemplate restTemplate;

    @GetMapping("/account")
    @Timed
    public ResponseEntity<SmallUserDTO> getAccount(@RequestHeader HttpHeaders headers) {
        log.debug("REST request to get account");
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        Optional<SmallUserDTO> user = Optional.empty();
        if(login.isPresent())
        {
             List<SmallUser> users = smallUserRepository.findByUsername(login.get());
             if(!users.isEmpty())
             {
            	 SmallUser su = users.get(0);
            	 user = Optional.of(new SmallUserDTO(su.getId(), su.getUsername(), su.getMunicipality_id(),
            			 su.getElectoral_district_id(), su.getRole()));
             }
        }
        return ResponseUtil.wrapOrNotFound(user);

       /* ResponseEntity<SmallUser> responseEntity =
            restTemplate.exchange("http://msrodo/api/account", HttpMethod.GET, new HttpEntity<>(headers),
            SmallUser.class);
        return responseEntity;*/
    }

    @PostMapping("/account/change-password")
    @Timed
    public void changePassword(@RequestBody String password) {

        log.debug("REST request to change password");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        //Optional<SmallUser> user = Optional.empty();
        if(login.isPresent())
        {
             List<SmallUser> users = smallUserRepository.findByUsername(login.get());
             if(!users.isEmpty())
             {
                 SmallUser user = users.get(0);
                 user.setPassword(encoder.encode(password));
                 smallUserRepository.save(user);
             }
        }
    }


    /**
     * POST  /users : Create a new myUser.
     *
     * @param user the myUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new myUser, or with status 400 (Bad Request) if the myUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/users")
    @Timed
    public ResponseEntity<UserDTO> createMyUser(@Valid @RequestBody UserDTO user) throws URISyntaxException {
        log.debug("REST request to save MyUser : {}", user);
        if (user.getId() != null) {
            throw new BadRequestAlertException("A new myUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        user.setId(UUIDs.timeBased());
        SmallUser smallUser = new SmallUser(user);
        RodoUser rodoUser = new RodoUser(new RodoUserDTO(user));
        // TODO Nie jestem pewien czy to powinno zwracac userDTO
        // TODO Dorobic zapis w bazie rodo

        SmallUser sm = smallUserRepository.save(smallUser);



        return ResponseEntity.created(new URI("/api/users/" + user.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, user.getId().toString()))
            .body(user);
    }

    /**
     * PUT  /users : Updates an existing myUser.
     *
     * @param user the myUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated myUser,
     * or with status 400 (Bad Request) if the myUser is not valid,
     * or with status 500 (Internal Server Error) if the myUser couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/users")
    @Timed
    public ResponseEntity<UserDTO> updateMyUser(@Valid @RequestBody UserDTO user) throws URISyntaxException {
        log.debug("REST request to update MyUser : {}", user);
        if (user.getId() == null) {
            return createMyUser(user);
        }

        SmallUser smallUser = new SmallUser(user);
        RodoUser rodoUser = new RodoUser(new RodoUserDTO(user));
        // TODO przerobic by tworzony byl tez
        // TODO podzial na 2 czesci i obie zapisac
        //SmallUser result = smallUserRepository.save(user);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, user.getId().toString()))
            .body(user);
    }

    /**
     * GET  /users : get all the Users.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of myUsers in body
     */
    @GetMapping("/users")
    @Timed
    public Page<SmallUserDTO> getAllUsers(Pageable pageRequest) {
        log.debug("REST request to get all MyUsers");

        Page<SmallUser> sur = smallUserRepository.findAllPaged(pageRequest);
        List<SmallUser> smallUsers = sur.getContent();
        List<SmallUserDTO> smallUsersDTO = new ArrayList<SmallUserDTO>();
        SmallUserDTO temp;
        for (SmallUser su : smallUsers)
        {
        	temp = new SmallUserDTO(su.getId(), su.getUsername(), su.getMunicipality_id(),
        			su.getElectoral_district_id(), su.getRole());
        	smallUsersDTO.add(temp);
        }
        
        return new PageImpl<SmallUserDTO>(smallUsersDTO);

        /* Page<MyUser> page = myUserRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);*/
    }





    /**
     * GET  /users/:id : get the "id" myUser.
     *
     * @param id the id of the myUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the myUser, or with status 404 (Not Found)
     */
    @GetMapping("/users/{id}")
    @Timed
    public ResponseEntity<SmallUserDTO> getMyUser(@PathVariable UUID id) {
        log.debug("REST request to get MyUser : {}", id);
        SmallUser u = smallUserRepository.findOne(id);
        SmallUserDTO user = new SmallUserDTO(u.getId(), u.getUsername(),
        		u.getMunicipality_id(), u.getElectoral_district_id(), u.getRole());
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(user));
    }

    /**
     * DELETE  /users/:id : delete the "id" myUser.
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

    @PostMapping("/users/{id}/disable")
    @Timed
    public ResponseEntity<Void> disableMyUser(@PathVariable UUID id) {
        log.debug("REST request to disable MyUser : {}", id);
        smallUserRepository.disable(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("Zablokowano konto użytkownika o id "+id, id.toString()))
            .build();
    }

    @GetMapping("/municipalities/{municipalityId}/users")
    @Timed
    public List<SmallUserDTO> getSmallUserByMunicipalityId(@PathVariable UUID municipalityId) {
        log.debug("REST request to get SmallUser by municipalityId : {}", municipalityId);

        List<SmallUser> smallUsers = smallUserRepository.findInMunicipality(municipalityId);

        List<SmallUserDTO> result = new ArrayList<SmallUserDTO>();

        for (SmallUser su : smallUsers)
        {
        	SmallUserDTO s = new SmallUserDTO(su.getId(), su.getUsername(), su.getMunicipality_id(),
        			su.getElectoral_district_id(), su.getRole());
        	result.add(s);
        }

        return result;
    }

    @GetMapping("/districts/{districtId}/users")
    @Timed
    public List<SmallUserDTO> getSmallUserByDistrictId(@PathVariable UUID districtId) {
        log.debug("REST request to get SmallUser by districtId: {}", districtId);

        List<SmallUser> smallUsers = smallUserRepository.findInDistrict(districtId);

        List<SmallUserDTO> result = new ArrayList<SmallUserDTO>();

        for (SmallUser su : smallUsers)
        {
        	SmallUserDTO s = new SmallUserDTO(su.getId(), su.getUsername(), su.getMunicipality_id(),
        			su.getElectoral_district_id(), su.getRole());
        	result.add(s);
        }

        return result;

    }


    @GetMapping("/users/small/{id}")
    @Timed
    public ResponseEntity<SmallUserDTO> getSmallUser(@PathVariable UUID id) {
        log.debug("REST request to get SmallUser : {}", id);
        SmallUser su = smallUserRepository.findOne(id);

        SmallUserDTO user = new SmallUserDTO(su.getId(), su.getUsername(), su.getMunicipality_id(), su.getElectoral_district_id(), su.getRole());

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(user));
    }

   /*
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
    }*/



  /*  @GetMapping("/electoral-periods")
    @Timed
    public List<ElectoralPeriod> getElectoralPeriods() {
        log.debug("REST request to get electoralPeriods");
        return Arrays.asList(
            new ElectoralPeriod().name("PreElectionPeriod").startDate(new LocalDate() .now().minusDays(1)).endDate
                (LocalDate.now().plusDays(1)),
            new ElectoralPeriod().name("FirstRoundPeriod").startDate(LocalDate.now().plusDays(1)).endDate(LocalDate
                .now() .plusDays(3)),
            new ElectoralPeriod().name("MidRoundPeriod").startDate(LocalDate.now().plusDays(3)).endDate(LocalDate
                .now() .plusDays(5)),
            new ElectoralPeriod().name("SecondRoundPeriod").startDate(LocalDate.now().plusDays(5)).endDate(LocalDate
                .now() .plusDays(7)),
            new ElectoralPeriod().name("PostElectionPeriod").startDate(LocalDate.now().plusDays(7)).endDate(LocalDate
                .MAX)
        );
    }*/


}
