package org.voting.gateway.domain;

import java.util.UUID;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(name = "voting_data",keyspace = "rso",
caseSensitiveKeyspace = false,
caseSensitiveTable = false)
public class VotingData {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    @Column(name = "voting_id")
    private UUID id;

    @Column(name = "is_voting_finished")
    private boolean isVotingFinished;

    @Column(name = "no_can_vote")
    private int noCanVote;

    @Column(name = "no_cards_used")
    private int noCardsUsed;

    @Column(name = "turn")
    private UUID turn;

    @Column(name = "ward")
    private UUID ward;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

    public UUID getTurn() {
        return turn;
    }

    public void setTurn(UUID turn) {
        this.turn = turn;
    }

    public UUID getWard() {
        return ward;
    }

    public void setWard(UUID ward) {
        this.ward = ward;
    }

    public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
