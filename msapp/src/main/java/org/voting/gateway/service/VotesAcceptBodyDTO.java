package org.voting.gateway.service;

import java.io.Serializable;

public class VotesAcceptBodyDTO implements Serializable{

	private int no_can_vote;
	private int no_cards_used;

	public int getNo_can_vote() {
		return no_can_vote;
	}
	public void setNo_can_vote(int no_can_vote) {
		this.no_can_vote = no_can_vote;
	}
	public int getNo_cards_used() {
		return no_cards_used;
	}
	public void setNo_cards_used(int no_cards_used) {
		this.no_cards_used = no_cards_used;
	}


}
