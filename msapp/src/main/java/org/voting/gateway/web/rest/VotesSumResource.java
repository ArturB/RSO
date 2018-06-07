package org.voting.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.voting.gateway.domain.Candidate;
import org.voting.gateway.repository.ElectoralDistrictRepository;
import org.voting.gateway.repository.MunicipalityRepository;
import org.voting.gateway.repository.VotesSumRepository;
import org.voting.gateway.service.PerCandidateVotesDTO;
import org.voting.gateway.service.VotesDesignationPackDTO;
import org.voting.gateway.service.VotesResultDTO;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.groupingBy;

/**
 * Created by defacto on 5/22/2018.
 */
@RestController
@RequestMapping("/api")
public class VotesSumResource {
    private final Logger log = LoggerFactory.getLogger(LoginDataResource.class);
    private final VotesSumRepository votesSumRepository;

    public VotesSumResource(VotesSumRepository votesSumRepository) {
        this.votesSumRepository = votesSumRepository;
    }

    @GetMapping("municipalities/{municipalityId}/{round}/votesSum")
    @Timed
    public ResponseEntity<VotesResultDTO> getVotesFromMunicipality(@PathVariable UUID municipalityId,
                                                           @PathVariable UUID round) {

        log.debug("REST request to get all votes sum from Municipality {} and round {}", municipalityId, round);
        VotesResultDTO votesResultDTO = votesSumRepository.getAllVotesInMunicipality( municipalityId, round);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(votesResultDTO));

        //return new
    	/*
    	return electoralDistrictRepository.findAll()
            .stream()
            .filter(c -> c.getMunicipality().getElectoral_district_id().equals(municipalityId))
            .flatMap(district -> getVotesFromElectoralDistrict(district.getElectoral_district_id(), round).stream())
            .collect(groupingBy(PerCandidateVotes::getCandidate_id))
            .entrySet()
            .stream()
            .map(e -> {
                PerCandidateVotes vote = new PerCandidateVotes();
                vote.setType(e.getValue().get(0).getType());
                vote.setNumber_of_votes(e.getValue().stream().mapToInt(PerCandidateVotes::getNumber_of_votes).sum());
                vote.setCandidate_id(e.getValue().get(0).getCandidate_id());
                return vote;
            })
            .collect(Collectors.toList());
            */
    }

    @GetMapping("districts/{districtId}/{round}/votesSum")
    @Timed
    public ResponseEntity<VotesResultDTO> getVotesFromElectoralDistrict(
            @PathVariable UUID districtId,
            @PathVariable UUID round) {
        log.debug("REST request to get all votes sum from district {} and round {}", districtId, round);

        VotesResultDTO votesResultDTO = votesSumRepository.getAllVotesInDistrict(districtId, round);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(votesResultDTO));
    }


}
