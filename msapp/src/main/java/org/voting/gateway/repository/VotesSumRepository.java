package org.voting.gateway.repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.ElectoralDistrict;
import org.voting.gateway.service.VotesDesignationPackDTO;
import org.voting.gateway.service.VotesDesignationSingleCandidateDTO;
import org.voting.gateway.service.VotesResultDTO;

@Repository
public class VotesSumRepository {

    private final ElectoralDistrictRepository electoralDistrictRepository;
    private final BPlanVotesDesignationPackRepository designationPackRepository;

    public VotesSumRepository(ElectoralDistrictRepository electoralDistrictRepository, BPlanVotesDesignationPackRepository designationPackRepository) {
        this.electoralDistrictRepository = electoralDistrictRepository;
        this.designationPackRepository = designationPackRepository;
    }

    public VotesResultDTO getAllVotesInDistrict(UUID districtId, int turnNum) {
        List<VotesDesignationPackDTO> votes = designationPackRepository
            .findByTurnByDistrictId(districtId, turnNum);
        Map<UUID, ElectoralDistrict> districts = electoralDistrictRepository.findAll().stream()
            .collect(Collectors.toMap(c -> c.getElectoral_district_id(), c -> c));
        return new VotesResultDTO(
            votes.stream().mapToInt(c -> {
                if (turnNum == 1) {
                    return districts.get(c.getElectoral_district_id()).getFirst_turn_no_cards_used();
                } else if (turnNum == 2) {
                    return districts.get(c.getElectoral_district_id()).getSecond_turn_no_cards_used();
                } else {
                    throw new RuntimeException("E663 turn no " + turnNum);
                }
            }).sum(),
            votes.stream().mapToInt(c -> c.getTooManyMarksCardsUsed()).sum(),
            votes.stream().mapToInt(c -> c.getNone_marks_cards_used()).sum(),
            votes.stream().mapToInt(c -> c.getErasedMarksCardsUsed()).sum(),
            votes.stream().mapToInt(c -> {
                if (turnNum == 1) {
                    return districts.get(c.getElectoral_district_id()).getFirst_turn_no_can_vote();
                } else if (turnNum == 2) {
                    return districts.get(c.getElectoral_district_id()).getSecond_turn_no_can_vote();
                } else {
                    throw new RuntimeException("E663 turn no " + turnNum);
                }
            }).sum(),

            votes.stream()
                .flatMap(c -> c.getCandidate_votes().stream())
                .map(c -> c.getCandidate_id())
                .distinct()
                .map(c -> {
                    int sum = votes.stream()
                        .flatMap(k -> k.getCandidate_votes().stream())
                        .filter(k -> k.getCandidate_id().equals(c))
                        .mapToInt(k -> k.getNumber_of_votes()).sum();
                    return new VotesDesignationSingleCandidateDTO(c, sum);
                }).collect(Collectors.toList()));
    }

    public VotesResultDTO getAllVotesInMunicipality(UUID municipalityId, int turnNum) {
        List<VotesResultDTO> votes= electoralDistrictRepository.findInMunicipality(municipalityId).stream()
            .map(c -> getAllVotesInDistrict(c.getElectoral_district_id(), turnNum)).collect(Collectors.toList());
        return new VotesResultDTO(
            votes.stream().mapToInt(c -> c.getNr_cards_used()).sum(),
            votes.stream().mapToInt(c -> c.getToo_many_marks_cards_used()).sum(),
            votes.stream().mapToInt(c -> c.getNone_marks_cards_used()).sum(),
            votes.stream().mapToInt(c -> c.getErased_marks_cards_used()).sum(),
            votes.stream().mapToInt(c -> c.getNo_can_vote()).sum(),
            votes.stream()
                .flatMap(c -> c.getCandidate_votes().stream())
                .map(c -> c.getCandidate_id())
                .distinct()
                .map(c -> {
                    int sum = votes.stream()
                        .flatMap(k -> k.getCandidate_votes().stream())
                        .filter(k -> k.getCandidate_id().equals(c))
                        .mapToInt(k -> k.getNumber_of_votes()).sum();
                    return new VotesDesignationSingleCandidateDTO(c, sum);
                }).collect(Collectors.toList()));
    }
}
