package org.voting.gateway.web.rest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.voting.gateway.domain.Candidate;
import org.voting.gateway.domain.SmallUser;
import org.voting.gateway.repository.ElectoralPeriodsRepository;
import org.voting.gateway.repository.SmallUserRepository;
import org.voting.gateway.security.SecurityUtils;

import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {
	
    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private static final String ENTITY_NAME = "Account";

    private final SmallUserRepository smallUserRepository;

    public AccountResource(SmallUserRepository smallUserRepository) {
        this.smallUserRepository = smallUserRepository;

    }
    
    /**
     * GET  /account gets the accoundt of current user.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the candidate, or with status 404 (Not Found)
     */
    
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
    
    
}
