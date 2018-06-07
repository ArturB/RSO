package org.voting.gateway.domain;



import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;


@Table(name = "electoral_period",
    keyspace = "rso",
    caseSensitiveKeyspace = false,
    caseSensitiveTable = false)
public class ElectoralPeriod implements Serializable {



    @PartitionKey
    @Column(name = "period_id")
    private UUID id;


    @Column(name = "period_name")
    private String name;

    @Column(name = "date_to")
    private Date endDate;

    @Column(name = "date_from")
    private Date startDate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
    	return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public ElectoralPeriod name(String name) {
        this.name = name;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public ElectoralPeriod startDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ElectoralPeriod endDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }


}
