package org.voting.gateway.service;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class VotesDesignationPackDTO implements Serializable {

	private List<VotesDesignationSingleCandidateDTO>  candidate_votes;
	private String date;

	private int electoralDistrictId;
	private int userId;
	private int tooManyMarksCardsUsed;
	private int noneMarksCardsUsed;
	private int erasedMarksCardsUsed;
	public List<VotesDesignationSingleCandidateDTO> getCandidate_votes() {
		return candidate_votes;
	}
	public void setCandidate_votes(List<VotesDesignationSingleCandidateDTO> candidate_votes) {
		this.candidate_votes = candidate_votes;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	public int getElectoralDistrictId() {
		return electoralDistrictId;
	}
	public void setElectoralDistrictId(int electoralDistrictId) {
		this.electoralDistrictId = electoralDistrictId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getTooManyMarksCardsUsed() {
		return tooManyMarksCardsUsed;
	}
	public void setTooManyMarksCardsUsed(int tooManyMarksCardsUsed) {
		this.tooManyMarksCardsUsed = tooManyMarksCardsUsed;
	}
	public int getNoneMarksCardsUsed() {
		return noneMarksCardsUsed;
	}
	public void setNoneMarksCardsUsed(int noneMarksCardsUsed) {
		this.noneMarksCardsUsed = noneMarksCardsUsed;
	}
	public int getErasedMarksCardsUsed() {
		return erasedMarksCardsUsed;
	}
	public void setErasedMarksCardsUsed(int erasedMarksCardsUsed) {
		this.erasedMarksCardsUsed = erasedMarksCardsUsed;
	}




}
