package org.voting.gateway.web.rest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.voting.gateway.domain.RodoUser;
import org.voting.gateway.repository.RodoUserRepository;
import org.voting.gateway.security.AESEncrypter;
import org.voting.gateway.service.RodoUserDTO;
import org.voting.gateway.service.RodoUserEncrypter;
import org.voting.gateway.web.rest.util.HeaderUtil;

import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class RodoUserResource {

    private final Logger log = LoggerFactory.getLogger(RodoUserResource.class);
    private final RodoUserRepository rodoUserRepository;
    private final RodoUserEncrypter encrypter;

    private static final String ENTITY_NAME = "RodoUser";

    public RodoUserResource(RodoUserRepository rodoUserRepository, RodoUserEncrypter encrypter) {
    	this.rodoUserRepository = rodoUserRepository;
    	this.encrypter = encrypter;
    }

    @GetMapping("/rodoUser/{id}")
    public ResponseEntity<RodoUserDTO> getRodoUser(@PathVariable UUID id)
    {
    	log.debug("REST request to get rodo user data by user id : {}", id);
    	RodoUserDTO rodoUser = null;

    	rodoUser = encrypter.decryptUserData(rodoUserRepository.findOne(id));

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rodoUser));
    }

    @PutMapping("/saveRodoUser")
    public ResponseEntity<Void> saveRodoUser(@Valid @RequestBody RodoUserDTO rodoUser)
    {
    	rodoUserRepository.save(encrypter.encryptUserData(rodoUser));
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rodoUser.getId().toString())).build();
    }

    @DeleteMapping("/deleteRodoUser/{id}")
    public ResponseEntity<Void> deleteRodoUser(@PathVariable UUID id)
    {
    	log.debug("REST request to delete rodo user data by user id : {}", id);
    	rodoUserRepository.delete(id);
    	return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


}
