package org.voting.gateway.domain;

import java.util.List;

/**
 * Created by defacto on 6/3/2018.
 */
public class VotesResult {
    private List<PerCandidateVotes> candidate_votes;
    private String type;
    private Integer too_many_marks_cards_used;
    private Integer none_marks_cards_used;
    private Integer erased_marks_cards_used;
    private Integer no_can_vote;
    private Integer nr_cards_used;

    public VotesResult() {
    }

    public List<PerCandidateVotes> getCandidate_votes() {
        return candidate_votes;
    }

    public void setCandidate_votes(List<PerCandidateVotes> candidate_votes) {
        this.candidate_votes = candidate_votes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getToo_many_marks_cards_used() {
        return too_many_marks_cards_used;
    }

    public void setToo_many_marks_cards_used(Integer too_many_marks_cards_used) {
        this.too_many_marks_cards_used = too_many_marks_cards_used;
    }

    public Integer getNone_marks_cards_used() {
        return none_marks_cards_used;
    }

    public void setNone_marks_cards_used(Integer none_marks_cards_used) {
        this.none_marks_cards_used = none_marks_cards_used;
    }

    public Integer getErased_marks_cards_used() {
        return erased_marks_cards_used;
    }

    public void setErased_marks_cards_used(Integer erased_marks_cards_used) {
        this.erased_marks_cards_used = erased_marks_cards_used;
    }

    public Integer getNo_can_vote() {
        return no_can_vote;
    }

    public void setNo_can_vote(Integer no_can_vote) {
        this.no_can_vote = no_can_vote;
    }

    public Integer getNr_cards_used() {
        return nr_cards_used;
    }

    public void setNr_cards_used(Integer nr_cards_used) {
        this.nr_cards_used = nr_cards_used;
    }
}
