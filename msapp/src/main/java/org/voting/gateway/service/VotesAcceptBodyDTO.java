package org.voting.gateway.service;

import java.io.Serializable;

public class VotesAcceptBodyDTO implements Serializable{

	private int noCanVote;
	private int noCardsUsed;
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
	
	
}
