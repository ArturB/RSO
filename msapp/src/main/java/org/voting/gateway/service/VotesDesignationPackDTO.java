package org.voting.gateway.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class VotesDesignationPackDTO implements Serializable {

    private UUID id;
	private List<VotesDesignationSingleCandidateDTO>  candidate_votes;
	private Date date;

	private UUID electoral_district_id;
	private UUID user_id;
	private int too_many_marks_cards_used;
	private int none_marks_cards_used;
	private int erased_marks_cards_used;



	public List<VotesDesignationSingleCandidateDTO> getCandidate_votes() {
		return candidate_votes;
	}


	public void setCandidate_votes(List<VotesDesignationSingleCandidateDTO> candidate_votes) {
		this.candidate_votes = candidate_votes;
	}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UUID getElectoral_district_id() {
        return electoral_district_id;
    }

    public void setElectoral_district_id(UUID electoral_district_id) {
        this.electoral_district_id = electoral_district_id;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public int getTooManyMarksCardsUsed() {
		return too_many_marks_cards_used;
	}
	public void setTooManyMarksCardsUsed(int tooManyMarksCardsUsed) {
		this.too_many_marks_cards_used = tooManyMarksCardsUsed;
	}
	public int getNone_marks_cards_used() {
		return none_marks_cards_used;
	}
	public void setNone_marks_cards_used(int none_marks_cards_used) {
		this.none_marks_cards_used = none_marks_cards_used;
	}
	public int getErasedMarksCardsUsed() {
		return erased_marks_cards_used;
	}
	public void setErasedMarksCardsUsed(int erasedMarksCardsUsed) {
		this.erased_marks_cards_used = erasedMarksCardsUsed;
	}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {

        this.id = id;

    }
}
