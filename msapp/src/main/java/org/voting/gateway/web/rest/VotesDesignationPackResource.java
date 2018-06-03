package org.voting.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.voting.gateway.domain.Candidate;
import org.voting.gateway.domain.MyUser;
import org.voting.gateway.domain.PerCandidateVotes;
import org.voting.gateway.domain.VotesDesignationPack;
import org.voting.gateway.repository.MyUserRepository;
import org.voting.gateway.security.SecurityUtils;
import org.voting.gateway.web.rest.util.HeaderUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by defacto on 6/3/2018.
 */

@RestController
@RequestMapping("/api")
public class VotesDesignationPackResource {
    private final Logger log = LoggerFactory.getLogger(VotesFromDistrictResource.class);
    private final MyUserRepository myUserRepository;
    private final CandidateResource candidateResource;

    public VotesDesignationPackResource(MyUserRepository myUserRepository, CandidateResource candidateResource) {
        this.myUserRepository = myUserRepository;
        this.candidateResource = candidateResource;
    }

    @PostMapping("/votes_designation_pack")
    @Timed
    public ResponseEntity<VotesDesignationPack> createVotesFromDistrict(@Valid @RequestBody VotesDesignationPack
                                                                        votesDesignationPack) throws URISyntaxException {
        log.debug("REST request to create VotesDesignationPack: {}", votesDesignationPack);
        //todo implement it
        return ResponseEntity.created(new URI("/api/votes-designation-pack/" + 23))
            .headers(HeaderUtil.createEntityCreationAlert("VotesDesignationPack", "23"))
            .body(votesDesignationPack);
    }


    @PutMapping("/votes_designation_pack")
    @Timed
    public ResponseEntity<VotesDesignationPack> updateVotesDesignationPack(
        @Valid @RequestBody VotesDesignationPack votesDesignationPack) throws URISyntaxException {
        log.debug("REST request to update VotesDesignationPack: {}", votesDesignationPack);
        if (votesDesignationPack.getId() == null) {
            return createVotesFromDistrict(votesDesignationPack);
        }
        //todo implement it
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("VotesDesignationPack", "23"))
            .body(votesDesignationPack);
    }

    @GetMapping("/votes_designation_pack/getFromUser/{round}/{userId}")
    @Timed
    public List<VotesDesignationPack> getVotesFromUsers(@PathVariable Long round, @PathVariable Long userId ) throws URISyntaxException {
        log.debug("REST request to get VotesDesignationPack: from round {} and userId ", round, userId);

        MyUser user = myUserRepository.findByUsername(SecurityUtils.getCurrentUserLogin().get()).get(0);

        Long municipalityId = user.getMunicipality().getId();
        List<Candidate> candidates = candidateResource.getCandidatesByMunicipalityIdWithRound( municipalityId, round);

        Random random = new Random((userId+1)*(round+1));

        List<VotesDesignationPack> outList = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            VotesDesignationPack pack = new VotesDesignationPack();
            pack.setId((long) random.nextInt(10000));
            pack.setDate(LocalDate.now().minus(Period.ofDays(i+1)));
            pack.setType("Type1");
            pack.setElectoral_district_id( (user.getElectoralDistrict().getId()));
            pack.setUser_id(user.getId());
            pack.setToo_many_marks_cards_used(random.nextInt(45));
            pack.setNone_marks_cards_used(random.nextInt(45));
            pack.setErased_marks_cards_used(random.nextInt(45));

            pack.setCandidate_votes(candidates.stream().map(c -> {
                PerCandidateVotes vote = new PerCandidateVotes();
                vote.setCandidate_id(c.getId());
                vote.setNumber_of_votes(random.nextInt(312));
                vote.setType("Zwykly");
                return vote;
            }).collect(Collectors.toList()));
            outList.add(pack);
        }
        return outList;
    }

}
