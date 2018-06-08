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
}
