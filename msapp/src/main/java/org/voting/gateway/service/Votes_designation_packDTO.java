package org.voting.gateway.service;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Votes_designation_packDTO implements Serializable {


    private UUID electoral_district_id;
    private int nr_cards_used;
    private int too_many_marks_cards_used;
    private int none_marks_cards_used;
    private int erased_marks_cards_used;
    private int no_can_vote;

    private List<Votes_designation_single_candidateDTO>  candidate_votes;

    public UUID getElectoral_district_id() {
        return electoral_district_id;
    }

    public void setElectoral_district_id(UUID electoral_district_id) {
        this.electoral_district_id = electoral_district_id;
    }

    public int getNr_cards_used() {
        return nr_cards_used;
    }

    public void setNr_cards_used(int nr_cards_used) {
        this.nr_cards_used = nr_cards_used;
    }

    public int getToo_many_marks_cards_used() {
        return too_many_marks_cards_used;
    }

    public void setToo_many_marks_cards_used(int too_many_marks_cards_used) {
        this.too_many_marks_cards_used = too_many_marks_cards_used;
    }

    public int getNone_marks_cards_used() {
        return none_marks_cards_used;
    }

    public void setNone_marks_cards_used(int none_marks_cards_used) {
        this.none_marks_cards_used = none_marks_cards_used;
    }

    public int getErased_marks_cards_used() {
        return erased_marks_cards_used;
    }

    public void setErased_marks_cards_used(int erased_marks_cards_used) {
        this.erased_marks_cards_used = erased_marks_cards_used;
    }

    public int getNo_can_vote() {
        return no_can_vote;
    }

    public void setNo_can_vote(int no_can_vote) {
        this.no_can_vote = no_can_vote;
    }

    public List<Votes_designation_single_candidateDTO> getCandidate_votes() {
        return candidate_votes;
    }

    public void setCandidate_votes(List<Votes_designation_single_candidateDTO> candidate_votes) {
        this.candidate_votes = candidate_votes;
    }
}
