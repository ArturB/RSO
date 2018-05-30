package org.voting.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.voting.gateway.domain.PerCandidateVotes;
import org.voting.gateway.repository.ElectoralDistrictRepository;
import org.voting.gateway.repository.MunicipalityRepository;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.groupingBy;

/**
 * Created by defacto on 5/22/2018.
 */
@RestController
@RequestMapping("/api")
public class VotesSumResource {
    private final Logger log = LoggerFactory.getLogger(LoginDataResource.class);
    private final CandidateResource candidateResource;
    private final MunicipalityRepository municipalityRepository;
    private final ElectoralDistrictRepository electoralDistrictRepository;

    public VotesSumResource(CandidateResource candidateResource, MunicipalityRepository municipalityRepository, ElectoralDistrictRepository electoralDistrictRepository) {
        this.candidateResource = candidateResource;
        this.municipalityRepository = municipalityRepository;
        this.electoralDistrictRepository = electoralDistrictRepository;
    }

    @GetMapping("municipalities/{municipalityId}/{round}/votesSum")
    @Timed
    public List<PerCandidateVotes> getVotesFromMuniciplity(@PathVariable Long municipalityId,
                                                           @PathVariable Long round) {
        return electoralDistrictRepository.findAll()
            .stream()
            .filter(c -> c.getMunicipality().getId().equals(municipalityId))
            .flatMap(district -> getVotesFromElectoralDistrict(district.getId(), round).stream())
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
    }

    @GetMapping("districts/{districtId}/{round}/votesSum")
    @Timed
    public List<PerCandidateVotes> getVotesFromElectoralDistrict(
            @PathVariable Long districtId,
            @PathVariable Long round) {
        Random random = new Random(districtId);
        log.debug("REST request to get all votes sum from district {} and round {}", districtId, round);

        Long municipalityId = electoralDistrictRepository.findOne(districtId).getMunicipality().getId();
        return candidateResource.getCandidatesByMunicipalityIdWithRound(municipalityId, round)
            .stream().map(c -> {
                PerCandidateVotes vote = new PerCandidateVotes();
                vote.setCandidate_id(c.getId());
                vote.setNumber_of_votes(random.nextInt(1000));
                vote.setType("Zwykły");
                return vote;
            }).collect(Collectors.toList());
    }

    @PostMapping("/districts/{districtId}/{round}/acceptVotes")
    @Timed
    public void acceptVotes(@PathVariable long districtId, @PathVariable long round){
        log.debug("REST request to accept votes from district: {} and round {}", districtId, round);
    }
}
