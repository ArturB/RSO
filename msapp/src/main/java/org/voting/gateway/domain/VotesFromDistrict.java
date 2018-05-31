package org.voting.gateway.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import com.datastax.driver.mapping.annotations.PartitionKey;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * A VotesFromDistrict.
 */
@Entity
@Table(name = "votes_from_district")
public class VotesFromDistrict implements Serializable {

    private static final long serialVersionUID = 1L;

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id;

    @PartitionKey
    @NotNull
    @Column(name = "electoral_district_id")
    private UUID id;    
    
    @Column(name = "accepted")
    private boolean isAccepted;
    
    @NotNull
    @Column(name = "candidate_id")
    private UUID candidateId;
    
    // ??? nie wiem jak data bedzie
    @Column(name = "date")
    private Date date;

    @NotNull
    @Column(name = "municipality_id")
    private UUID municipalityId;
    
    @NotNull
    @Min(value = 0)
    @Column(name = "number_of_votes")
    private Integer numberOfVotes;
    
    @Column(name = "type")
    private String type;
    
    @NotNull
    @Column(name = "user_id")
    private UUID userId;

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
    
    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public Boolean getIsAccepted() {
        return isAccepted;
    }
    
    public VotesFromDistrict isAccepted(Boolean isAccepted) {
    	this.isAccepted = isAccepted;
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
    
    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate1() {
        return date;
    }
    
    public VotesFromDistrict date(Date date) {
    	this.date = date;
    	return this;
    }
    
    public void setMunicipalityId(UUID municipalityId) {
        this.municipalityId = municipalityId;
    }

    public UUID getMunicipalityId() {
        return municipalityId;
    }
    
    public VotesFromDistrict municipalityId(UUID municipalityId) {
    	this.municipalityId = municipalityId;
    	return this;
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

    @Override
    public String toString() {
        return "VotesFromDistrict{" +
            "id=" + getId() + "'" +
        	", isAccepted=" + getIsAccepted() + "'" +
            ", candidateId=" + getCandidateId() + "'" +
        	", date=" + getDate1() + "'" +
            ", municipalityId=" + getMunicipalityId() + "'" +
        	", numberOfVotes=" + getNumberOfVotes() + "'" +
            ", type='" + getType() + "'" +
            ", userId=" + getUserId() +
            "}";
    }
}
