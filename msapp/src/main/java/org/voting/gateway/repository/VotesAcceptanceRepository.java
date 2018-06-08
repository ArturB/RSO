package org.voting.gateway.repository;

import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.*;

import org.voting.gateway.service.VotesAcceptBodyDTO;
import org.voting.gateway.service.VotesDesignationSingleCandidateDTO;
import org.voting.gateway.web.rest.errors.ErrorValue;
import org.voting.gateway.web.rest.errors.MyErrorException;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Repository
public class VotesAcceptanceRepository {
    private final ElectoralDistrictRepository electoralDistrictRepository;
    private final MunicipalityRepository municipalityRepository;
    private final VotesSumRepository votesSumRepository;

    public VotesAcceptanceRepository(ElectoralDistrictRepository electoralDistrictRepository, MunicipalityRepository municipalityRepository, VotesSumRepository votesSumRepository) {
        this.electoralDistrictRepository = electoralDistrictRepository;
        this.municipalityRepository = municipalityRepository;
        this.votesSumRepository = votesSumRepository;
    }

    public void acceptDistrict(UUID districtId, int turnNum, VotesAcceptBodyDTO votesAcceptBody) {
        ElectoralDistrict district = electoralDistrictRepository.findOne(districtId);
        if(turnNum == 1 ){
            district.setFirst_turn_completed(true);
            district.setFirst_turn_no_can_vote(votesAcceptBody.getNo_can_vote());
            district.setFirst_turn_no_cards_used(votesAcceptBody.getNo_cards_used());
        }else if (turnNum == 2){
            district.setSecond_turn_completed(true);
            district.setSecond_turn_no_can_vote(votesAcceptBody.getNo_can_vote());
            district.setSecond_turn_no_cards_used(votesAcceptBody.getNo_cards_used());
        }else{
            throw new RuntimeException("E983 turn num unexpected "+turnNum);
        }

        electoralDistrictRepository.save(district);
    }

    public void acceptMunicipality(UUID municipalityId, int turnNum) {
        Municipality municipality = municipalityRepository.findOne(municipalityId);

        boolean districtsAccepted = electoralDistrictRepository.findInMunicipality(municipalityId).stream().allMatch(c -> {
            if (turnNum == 1) {
                return c.getFirst_turn_completed();
            } else if (turnNum == 2) {
                return c.getSecond_turn_completed();
            } else {
                throw new RuntimeException("Ei123 " + turnNum);
            }
        });
        if(!districtsAccepted){
            throw new MyErrorException(ErrorValue.NOT_ALL_DISTRICTS_ACCEPTED);
        }

        if(turnNum == 1 ) {
            List<VotesDesignationSingleCandidateDTO> candidate_votes = votesSumRepository.getAllVotesInMunicipality
                (municipalityId, 1).getCandidate_votes();

            if (candidate_votes.size() <= 1) {
                municipality.setHas_second_round(false);
            }else{
                int winnerCount = candidate_votes.stream()
                    .sorted(Comparator.comparing(c -> c.getNumber_of_votes())).findFirst().get() .getNumber_of_votes();
                int sum = candidate_votes.stream().mapToInt(c -> c.getNumber_of_votes()).sum();

                municipality.setHas_second_round( winnerCount*2 > sum);
            }
        }

        if(turnNum == 1 ) {
            municipality.setFirst_turn_completed(true);
        }else if (turnNum == 2){
            municipality.setSecond_turn_completed(true);
        }else{
            throw new RuntimeException("E983 turn num unexpected "+turnNum);
        }

        municipalityRepository.save(municipality);
    }
}
