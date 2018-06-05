package org.voting.gateway.domain;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by defacto on 6/3/2018.
 */
public class VotesDesignationPack {
    private Long id;
    private List<PerCandidateVotes> candidate_votes;

    private LocalDate date;
    private String type;
    private Long electoral_district_id;
    private Long user_id;
    private Integer too_many_marks_cards_used;
    private Integer none_marks_cards_used;
    private Integer erased_marks_cards_used;

    public VotesDesignationPack() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PerCandidateVotes> getCandidate_votes() {
        return candidate_votes;
    }

    public void setCandidate_votes(List<PerCandidateVotes> candidate_votes) {
        this.candidate_votes = candidate_votes;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getElectoral_district_id() {
        return electoral_district_id;
    }

    public void setElectoral_district_id(Long electoral_district_id) {
        this.electoral_district_id = electoral_district_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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
}
