package org.voting.gateway.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    @GetMapping("/electoral-periods")
    @Timed
    public List<ElectoralPeriod> getAllElectorialPeriods() {
        log.debug("Rest request to get all electoral periods");
        return electoralPeriodsRepository.findAll();
    }

    @PostMapping("/electoral-periods/change")
    @Timed
    public void change(@RequestBody String currentPeriod) {
        log.debug("Rest request to changeelectoral periods to "+currentPeriod);

        List<ElectoralPeriod> allPeriods = electoralPeriodsRepository.findAll();
        ElectoralPeriod targetPeriod = allPeriods.stream().filter(c -> c.getName().equals(currentPeriod)).findFirst().get();
        Date currentDate = new Date();

        long centerTime = targetPeriod.getStartDate().getTime()
            + (targetPeriod.getEndDate().getTime() - targetPeriod .getStartDate ().getTime())/2;

        long delta = currentDate.getTime() - centerTime;

        for(ElectoralPeriod period : allPeriods){
            period.setStartDate(new Date(period.getStartDate().getTime()+delta));
            period.setEndDate(new Date(period.getEndDate().getTime()+delta));
            electoralPeriodsRepository.update(period);
        }
    }
}
