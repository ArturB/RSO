package org.voting.gateway.domain;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.Date;
import java.util.UUID;

/**
 * Created by defacto on 6/8/2018.
 */

@Table(name = "bPlanVotesDesignationPack",
    keyspace = "rso",
    caseSensitiveKeyspace = false,
    caseSensitiveTable = false)
public class BPlanVotesDesignationPack {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    @Column(name = "pack_id")
    private UUID pack_id;

    @Column(name = "position_name")
    private String positionName;

    @Column(name = "date")
    private Date date;

    @Column(name = "electoral_district_id")
    private UUID electoral_district_id;

    @Column(name = "user_id")
    private UUID user_id;

    @Column(name = " too_many_marks_cards_used")
    private int too_many_marks_cards_used;

    @Column(name = "erased_marks_cards_used")
    private int none_marks_cards_used;

    @Column(name = "erased_marks_cards_used")
    private int erased_marks_cards_used;

    @Column(name = "round")
    private int round;
}
