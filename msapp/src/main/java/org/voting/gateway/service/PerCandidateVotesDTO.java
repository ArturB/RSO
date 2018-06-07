package org.voting.gateway.service;

import java.io.Serializable;
import java.util.UUID;

public class PerCandidateVotesDTO implements Serializable{

	private int numberOfVotes;
	private String type;
	private UUID candidateId;
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

    public UUID getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(UUID candidateId) {
        this.candidateId = candidateId;
    }
}
