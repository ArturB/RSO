package org.voting.gateway.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.voting.gateway.repository.ElectoralPeriodsRepository;
import org.voting.gateway.repository.SmallUserRepository;

/**
 * REST controller for managing Account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {
	
    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private static final String ENTITY_NAME = "Account";

    private final SmallUserRepository accountRepository;

    public AccountResource(SmallUserRepository accountRepository) {
        this.accountRepository = accountRepository;

    }
}
