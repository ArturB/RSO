package org.voting.gateway.domain;

/**
 * Created by defacto on 6/3/2018.
 */
public class VotesAcceptBody {
    private Integer no_can_vote;
    private Integer no_cards_used;

    public Integer getNo_can_vote() {
        return no_can_vote;
    }

    public void setNo_can_vote(Integer no_can_vote) {
        this.no_can_vote = no_can_vote;
    }

    public Integer getNo_cards_used() {
        return no_cards_used;
    }

    public void setNo_cards_used(Integer no_cards_used) {
        this.no_cards_used = no_cards_used;
    }

    @Override
    public String toString() {
        return "VotesAcceptBody{" +
            "no_can_vote=" + no_can_vote +
            ", no_cards_used=" + no_cards_used +
            '}';
    }
}
