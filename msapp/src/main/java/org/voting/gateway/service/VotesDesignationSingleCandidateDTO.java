package org.voting.gateway.service;

import java.io.Serializable;
import java.util.UUID;

public class VotesDesignationSingleCandidateDTO implements Serializable {
    private UUID candidate_id;
    private int number_of_votes;

    public VotesDesignationSingleCandidateDTO() {
    }

    public VotesDesignationSingleCandidateDTO(UUID candidate_id, int number_of_votes) {
        this.candidate_id = candidate_id;
        this.number_of_votes = number_of_votes;
    }

    public UUID getCandidate_id() {
        return candidate_id;
    }

    public void setCandidate_id(UUID candidate_id) {
        this.candidate_id = candidate_id;
    }

    public int getNumber_of_votes() {
        return number_of_votes;
    }

    public void setNumber_of_votes(int number_of_votes) {
        this.number_of_votes = number_of_votes;
    }

}
