package org.voting.gateway.domain;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.UUID;

/**
 * Created by defacto on 6/8/2018.
 */
@Table(name = "bPlanSingleCandidateVote",
    keyspace = "rso",
    caseSensitiveKeyspace = false,
    caseSensitiveTable = false)
public class BPlanSingleCandidateVote {
    private static final long serialVersionUID = 1L;

    @PartitionKey
    @Column(name = "single_candidate_vote_id")
    private UUID single_candidate_vote_id;

    @Column(name = "candidate_id")
    private UUID candidate_id;

    @Column(name = "votes_count")
    private int votes_count;

    @Column(name = "pack_id")
    private UUID pack_id;

    public UUID getSingle_candidate_vote_id() {
        return single_candidate_vote_id;
    }

    public void setSingle_candidate_vote_id(UUID single_candidate_vote_id) {
        this.single_candidate_vote_id = single_candidate_vote_id;
    }

    public UUID getCandidate_id() {
        return candidate_id;
    }

    public void setCandidate_id(UUID candidate_id) {
        this.candidate_id = candidate_id;
    }

    public int getVotes_count() {
        return votes_count;
    }

    public void setVotes_count(int votes_count) {
        this.votes_count = votes_count;
    }

    public UUID getPack_id() {
        return pack_id;
    }

    public void setPack_id(UUID pack_id) {
        this.pack_id = pack_id;
    }
}
