package org.voting.gateway.domain;

import java.util.Date;
import java.util.UUID;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(name = "voting_results",keyspace = "rso",
caseSensitiveKeyspace = false,
caseSensitiveTable = false)
public class VotingResults {

    @PartitionKey
    @Column(name = "result_id")
    private UUID id;

    @Column(name = "candidate_id")
    private UUID candidateId;

    @Column(name = "candidate_name")
    private String candidateName;

    @Column(name = "candidate_surname")
    private String candidateSurname;

    @Column(name = "commune_id")
    private UUID communeId;

    @Column(name = "commune_name")
    private String communeName;

    @Column(name = "date_generated")
    private Date dateGenerated;

    @Column(name = "is_voting_finished")
    private boolean votingFinished;

    @Column(name = "no_can_vote")
    private int noCanVote;

    @Column(name = "no_cards_used")
    private int noCardsUsed;

    @Column(name = "no_of_votes")
    private int noOfVotes;

    @Column(name = "no_turn")
    private int noTurn;

    @Column(name = "turn_id")
    private int turnId;

    @Column(name = "vote_type")
    private String voteType;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

    public UUID getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(UUID candidateId) {
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

	public String getCommuneName() {
		return communeName;
	}

	public void setCommuneName(String communeName) {
		this.communeName = communeName;
	}

	public Date getDateGenerated() {
		return dateGenerated;
	}

	public void setDateGenerated(Date dateGenerated) {
		this.dateGenerated = dateGenerated;
	}

    public UUID getCommuneId() {
        return communeId;
    }

    public void setCommuneId(UUID communeId) {
        this.communeId = communeId;
    }

    public boolean isVotingFinished() {
        return votingFinished;
    }

    public void setVotingFinished(boolean votingFinished) {
        this.votingFinished = votingFinished;
    }

    public int getTurnId() {
        return turnId;
    }

    public void setTurnId(int turnId) {
        this.turnId = turnId;
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

}
