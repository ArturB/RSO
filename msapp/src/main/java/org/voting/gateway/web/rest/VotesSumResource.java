package org.voting.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.voting.gateway.domain.ElectoralDistrict;
import org.voting.gateway.repository.ElectoralDistrictRepository;
import org.voting.gateway.repository.VotesSumRepository;
import org.voting.gateway.service.VotesResultDTO;

import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.groupingBy;

/**
 * Created by defacto on 5/22/2018.
 */
@RestController
@RequestMapping("/api")
public class VotesSumResource {
    private final Logger log = LoggerFactory.getLogger(LoginDataResource.class);
    private final VotesSumRepository votesSumRepository;
    private final ElectoralDistrictRepository electoralDistrictRepository;

    public VotesSumResource(VotesSumRepository votesSumRepository, ElectoralDistrictRepository electoralDistrictRepository) {
        this.votesSumRepository = votesSumRepository;
        this.electoralDistrictRepository = electoralDistrictRepository;
    }

    @GetMapping("municipalities/{municipalityId}/{round}/votesSum")
    @Timed
    public ResponseEntity<VotesResultDTO> getVotesFromMunicipality(@PathVariable UUID municipalityId,
                                                           @PathVariable Integer round) {

        log.debug("REST request to get all votes sum from Municipality {} and round {}", municipalityId, round);
        VotesResultDTO votesResultDTO = votesSumRepository.getAllVotesInMunicipality( municipalityId, round);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(votesResultDTO));
    }

    @GetMapping("districts/debug")
    @Timed
    public ResponseEntity<VotesResultDTO> getVotesFromElectoralDistrictd(
        @PathVariable UUID districtId,
        @PathVariable Integer round) {
        for (ElectoralDistrict district : electoralDistrictRepository.findAll()) {

        }


        log.debug("REST request to get all votes sum from district {} and round {}", districtId, round);

        VotesResultDTO votesResultDTO = votesSumRepository.getAllVotesInDistrict(districtId, round);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(votesResultDTO));
    }

    @GetMapping("districts/{districtId}/{round}/votesSum")
    @Timed
    public ResponseEntity<VotesResultDTO> getVotesFromElectoralDistrict(
            @PathVariable UUID districtId,
            @PathVariable Integer round) {
        for (ElectoralDistrict district : electoralDistrictRepository.findAll()) {

        }


        log.debug("REST request to get all votes sum from district {} and round {}", districtId, round);

        VotesResultDTO votesResultDTO = votesSumRepository.getAllVotesInDistrict(districtId, round);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(votesResultDTO));
    }


}
