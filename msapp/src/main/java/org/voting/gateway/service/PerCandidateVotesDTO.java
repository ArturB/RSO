package org.voting.gateway.service;

import java.io.Serializable;

public class PerCandidateVotesDTO implements Serializable{
	
	private int numberOfVotes;
	private String type;
	private int candidateId;
	public int getNumberOfVotes() {
		return numberOfVotes;
	}
	public void setNumberOfVotes(int numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(int candidateId) {
		this.candidateId = candidateId;
	}
	
	

}
