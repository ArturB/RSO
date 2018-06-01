package org.voting.gateway.domain;

import java.util.UUID;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(name = "candidate",keyspace = "rso",
//readConsistency = "QUORUM",
//writeConsistency = "QUORUM",
caseSensitiveKeyspace = false,
caseSensitiveTable = false)
public class Turn {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    @Column(name = "turn_id")
    private UUID id;

    @Column(name = "age")
    private Short age;
    
    //@Column()
}
