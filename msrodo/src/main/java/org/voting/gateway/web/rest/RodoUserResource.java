package org.voting.gateway.web.rest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.voting.gateway.domain.RodoUser;
import org.voting.gateway.repository.RodoUserRepository;
import org.voting.gateway.service.LoginDataDTO;
import org.voting.gateway.web.rest.util.HeaderUtil;

import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class RodoUserResource {

    private final Logger log = LoggerFactory.getLogger(LoginDataResource.class);
    private final RodoUserRepository rodoUserRepository;
    
    private static final String ENTITY_NAME = "RodoUser";
    
    public RodoUserResource(RodoUserRepository rodoUserRepository) {
    	this.rodoUserRepository = rodoUserRepository;
    }
    
    @GetMapping("rodoUser/{id}")
    public ResponseEntity<RodoUser> getRodoUser(@PathVariable UUID id)
    {
    	log.debug("REST request to get rodo user data by user id : {}", id);
    	RodoUser rodoUser = null;
    	
    	RodoUser userData = rodoUserRepository.findOne(id);
    	if (userData != null)
    		rodoUser = userData;
    	
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rodoUser));
    }
    
    @GetMapping("saveRodoUser/{id}")
    public ResponseEntity<RodoUser> saveRodoUser(@PathVariable RodoUser rodoUser)
    {
    	rodoUserRepository.save(rodoUser);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rodoUser.getId().toString()))
                .body(rodoUser);
    }
        
    @GetMapping("deleteRodoUser/{id}")
    public ResponseEntity<Void> deleteRodoUser(@PathVariable UUID id)
    {
    	log.debug("REST request to delete rodo user data by user id : {}", id);
    	rodoUserRepository.delete(id);    	
    	return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    
}