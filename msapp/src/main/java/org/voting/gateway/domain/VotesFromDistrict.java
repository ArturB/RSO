package org.voting.gateway.domain;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.io.Serializable;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * A VotesFromDistrict.
 */

@Table(name = "votes_from_ward",
    keyspace = "rso",
    caseSensitiveKeyspace = false,
    caseSensitiveTable = false)
public class VotesFromDistrict implements Serializable {

    private static final long serialVersionUID = 1L;


    @PartitionKey
    @Column(name = "votes_id")
    private UUID id;



    @Column(name = "candidate")
    private UUID candidateId;


    @Column(name = "date")
    private Date date;


    @Column(name = "voting_data")
    private UUID votingDataId;


    @Column(name = "no_votes")
    private Integer numberOfVotes;

    @Column(name = "type")
    private String type;


    @Column(name = "user")
    private UUID userId;


    public VotesFromDistrict() {
    }

    public VotesFromDistrict(UUID id, UUID candidateId, Date date, UUID votingDataId, Integer numberOfVotes, String type, UUID userId) {
        this.id = id;
        this.candidateId = candidateId;
        this.date = date;
        this.votingDataId = votingDataId;
        this.numberOfVotes = numberOfVotes;
        this.type = type;
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public VotesFromDistrict id(UUID id){
    	this.id = id;
    	return this;
    }





    public void setCandidateId(UUID candidateId) {
        this.candidateId = candidateId;
    }

    public UUID getCandidateId() {
        return candidateId;
    }

    public VotesFromDistrict candidateId(UUID candidateId) {
    	this.candidateId = candidateId;
    	return this;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UUID getVotingDataId() {
        return votingDataId;
    }

    public void setVotingDataId(UUID votingDataId) {
        this.votingDataId = votingDataId;
    }

    public void setNumberOfVotes(Integer numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    public Integer getNumberOfVotes() {
        return numberOfVotes;
    }

    public VotesFromDistrict numberOfVotes(Integer numberOfVotes) {
    	this.numberOfVotes = numberOfVotes;
    	return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public VotesFromDistrict type(String type) {
    	this.type = type;
    	return this;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }

    public VotesFromDistrict userId(UUID userId) {
    	this.userId = userId;
    	return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VotesFromDistrict votesFromDistrict = (VotesFromDistrict) o;
        if (votesFromDistrict.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), votesFromDistrict.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }


}
