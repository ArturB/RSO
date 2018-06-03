package org.voting.gateway.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.voting.gateway.domain.Candidate;
import org.voting.gateway.domain.ElectoralPeriod;
import org.voting.gateway.repository.CandidateRepository;
import org.voting.gateway.repository.ElectoralPeriodsRepository;
import org.voting.gateway.web.rest.errors.BadRequestAlertException;
import org.voting.gateway.web.rest.util.HeaderUtil;

import com.codahale.metrics.annotation.Timed;
import com.datastax.driver.core.utils.UUIDs;

/**
 * REST controller for managing ElectorialPeriods.
 */
@RestController
@RequestMapping("/api")
public class ElectoralPeriodsResource {
	
    private final Logger log = LoggerFactory.getLogger(ElectoralPeriodsResource.class);

    private static final String ENTITY_NAME = "electoralPeriods";

    private final ElectoralPeriodsRepository electoralPeriodsRepository;

    public ElectoralPeriodsResource(ElectoralPeriodsRepository electoralPeriodsRepository) {
        this.electoralPeriodsRepository = electoralPeriodsRepository;

    }
    
    /**
     * GET  /electoral-periods/ : get all electoral periods.
     *
     * @return returns all electoral periods
     */
    @PostMapping("/electoral-periods/")
    @Timed
    public List<ElectoralPeriod> getAllElectorialPeriods() {
        log.debug("Rest request to get all electoral periods");    
        return electoralPeriodsRepository.findAll();
    }
}
