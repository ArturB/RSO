package org.voting.gateway.domain;

import java.util.Date;
import java.util.UUID;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(name = "candidate",keyspace = "rso",
caseSensitiveKeyspace = false,
caseSensitiveTable = false)
public class VotingReport {

    @PartitionKey
    @Column(name = "report_id")
    private UUID id;

    @Column(name = "candidate_id")
    private Short candidateId;
    
    @Column(name = "candidate_name")
    private String candidateName;
    
    @Column(name = "candidate_surname")
    private String candidateSurname;
    
    @Column(name = "commune_name")
    private Short communeName;
    
    @Column(name = "date_generated")
    private Date dateGenerated;
    
    @Column(name = "is_voting_finished")
    private boolean isVotingFinished;
    
    @Column(name = "no_can_vote")
    private int noCanVote;
    
    @Column(name = "no_cards_used")
    private int noCardsUsed;
    
    @Column(name = "no_of_votes")
    private int noOfVotes;
    
    @Column(name = "no_turn")
    private int noTurn;
    
    @Column(name = "vote_type")
    private String voteType;
    
    @Column(name = "ward_name")
    private String wardName;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Short getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Short candidateId) {
		this.candidateId = candidateId;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getCandidateSurname() {
		return candidateSurname;
	}

	public void setCandidateSurname(String candidateSurname) {
		this.candidateSurname = candidateSurname;
	}

	public Short getCommuneName() {
		return communeName;
	}

	public void setCommuneName(Short communeName) {
		this.communeName = communeName;
	}

	public Date getDateGenerated() {
		return dateGenerated;
	}

	public void setDateGenerated(Date dateGenerated) {
		this.dateGenerated = dateGenerated;
	}

	public boolean isVotingFinished() {
		return isVotingFinished;
	}

	public void setVotingFinished(boolean isVotingFinished) {
		this.isVotingFinished = isVotingFinished;
	}

	public int getNoCanVote() {
		return noCanVote;
	}

	public void setNoCanVote(int noCanVote) {
		this.noCanVote = noCanVote;
	}

	public int getNoCardsUsed() {
		return noCardsUsed;
	}

	public void setNoCardsUsed(int noCardsUsed) {
		this.noCardsUsed = noCardsUsed;
	}

	public int getNoOfVotes() {
		return noOfVotes;
	}

	public void setNoOfVotes(int noOfVotes) {
		this.noOfVotes = noOfVotes;
	}

	public int getNoTurn() {
		return noTurn;
	}

	public void setNoTurn(int noTurn) {
		this.noTurn = noTurn;
	}

	public String getVoteType() {
		return voteType;
	}

	public void setVoteType(String voteType) {
		this.voteType = voteType;
	}

	public String getWardName() {
		return wardName;
	}

	public void setWardName(String wardName) {
		this.wardName = wardName;
	}
    
    
}
