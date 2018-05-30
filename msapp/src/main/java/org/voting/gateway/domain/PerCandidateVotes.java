package org.voting.gateway.domain;

/**
 * Created by defacto on 5/22/2018.
 */
public class PerCandidateVotes {
    private int number_of_votes;
    private String type;
    private Long candidate_id;

    public int getNumber_of_votes() {
        return number_of_votes;
    }

    public void setNumber_of_votes(int number_of_votes) {
        this.number_of_votes = number_of_votes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCandidate_id() {
        return candidate_id;
    }

    public void setCandidate_id(Long candidate_id) {
        this.candidate_id = candidate_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PerCandidateVotes that = (PerCandidateVotes) o;

        if (number_of_votes != that.number_of_votes) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        return candidate_id != null ? candidate_id.equals(that.candidate_id) : that.candidate_id == null;
    }

    @Override
    public int hashCode() {
        int result = number_of_votes;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (candidate_id != null ? candidate_id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PerCandidateVotes{" +
            "number_of_votes=" + number_of_votes +
            ", type='" + type + '\'' +
            ", candidate_id=" + candidate_id +
            '}';
    }
}
