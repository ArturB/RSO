package org.voting.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;

import com.datastax.driver.core.utils.UUIDs;
import io.github.jhipster.web.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.voting.gateway.domain.Candidate;
import org.voting.gateway.domain.ElectoralDistrict;
import org.voting.gateway.domain.SmallUser;
import org.voting.gateway.repository.*;
import org.voting.gateway.service.VotesDesignationPackDTO;
import org.voting.gateway.service.VotesDesignationSingleCandidateDTO;
import org.voting.gateway.service.VotesResultDTO;

import java.util.*;

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
    private final MunicipalityRepository municipalityRepository;
    private final SmallUserRepository repository;
    private final BPlanVotesDesignationPackRepository designationPackRepository;
    private final CandidateRepository candidateRepository;

    public VotesSumResource(VotesSumRepository votesSumRepository, ElectoralDistrictRepository electoralDistrictRepository, MunicipalityRepository municipalityRepository, SmallUserRepository repository, BPlanVotesDesignationPackRepository designationPackRepository, CandidateRepository candidateRepository) {
        this.votesSumRepository = votesSumRepository;
        this.electoralDistrictRepository = electoralDistrictRepository;
        this.municipalityRepository = municipalityRepository;
        this.repository = repository;
        this.designationPackRepository = designationPackRepository;
        this.candidateRepository = candidateRepository;
    }

    @GetMapping("municipalities/{municipalityId}/{round}/votesSum")
    @Timed
    public ResponseEntity<VotesResultDTO> getVotesFromMunicipality(@PathVariable UUID municipalityId,
                                                           @PathVariable Integer round) {
        log.debug("REST request to get all votes sum from Municipality {} and round {}", municipalityId, round);
        VotesResultDTO votesResultDTO = votesSumRepository.getAllVotesInMunicipality( municipalityId, round);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(votesResultDTO));
    }

    @GetMapping("XXdistricts/debug")
    @Timed
    public ResponseEntity<VotesResultDTO> getVotesFromElectoralDistrictd(){

        Random random = new Random();
        for(SmallUser user : repository.findInMunicipality(UUID.fromString("82c80e71-db4b-4317-9764-6cf1cacccfd7"))){
            System.out.println("USER!!! ");
            if( user.getRole().equals("ROLE_OKW_MEMBER")){
                for(int i = 0; i < random.nextInt(2)+1; i++) {
                    VotesDesignationPackDTO votesPack = new VotesDesignationPackDTO();
                    votesPack.setUser_id(user.getId());
                    votesPack.setTooManyMarksCardsUsed(random.nextInt(100));
                    votesPack.setNone_marks_cards_used(random.nextInt(100));
                    votesPack.setErasedMarksCardsUsed(random.nextInt(100));
                    votesPack.setDate(new Date());
                    votesPack.setElectoral_district_id(user.getElectoral_district_id());
                    List<VotesDesignationSingleCandidateDTO> candidateVotes = new ArrayList<>();
                    for (Candidate candidate : candidateRepository.findInMunicipality(user.getMunicipality_id())) {
                        candidateVotes.add(new VotesDesignationSingleCandidateDTO(candidate.getCandidate_id(), random
                            .nextInt(500)));
                    }
                    votesPack.setCandidate_votes(candidateVotes);
                    votesPack.setCandidate_votes(candidateVotes);
                    UUID id = UUIDs.timeBased();
                    votesPack.setId(id);
                    designationPackRepository.add(votesPack, 1, user.getId() );
                }
            }
        }

//        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(votesResultDTO));
        return null;
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
