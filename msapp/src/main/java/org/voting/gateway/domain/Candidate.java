package org.voting.gateway.domain;



import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A Candidate.
 */

@Table(name = "candidate",keyspace = "rso",
    //readConsistency = "QUORUM",
    //writeConsistency = "QUORUM",
    caseSensitiveKeyspace = false,
    caseSensitiveTable = false)
public class Candidate implements Serializable {

    private static final long serialVersionUID = 1L;


    @PartitionKey
    @Column(name = "candidate_id")
    private UUID candidate_id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;
    
    @Column(name = "age")
    private int age;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "commune")
    private Short municipality_id;

    @Column(name = "party")
    private Short party_id;

    @Column(name = "turns")
    private List<UUID> turns;
    

    public UUID getId() {
        return candidate_id;
    }

    public void setId(UUID id) {
        this.candidate_id = id;
    }

    public Candidate id(UUID id) {
    	this.candidate_id = id;
    	return this;
    }

    public int getAge() {
        return age;
    }

    public Candidate age(Short age) {
        this.age = age;
        return this;
    }

    public void setAge(Short age) {
        this.age = age;
    }

    public Short getMunicipalityId() {
        return municipality_id;
    }

    public Candidate municipalityId(Short municipalityId) {
        this.municipality_id = municipalityId;
        return this;
    }

    public void setMunicipalityId(Short municipalityId)
    {
        this.municipality_id = municipalityId;
    }


    public String getName() {
        return name;
    }

    public Candidate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public Candidate Occupation(String occupation) {
        this.occupation = occupation;
        return this;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Short getPartyId() {
        return party_id;
    }

    public Candidate partyId(Short partyId) {
        this.party_id = partyId;
        return this;
    }

    public void setPartyId(Short partyId) {
        this.party_id = partyId;
    }

    public List<UUID> getTurns() {
        return turns;
    }

    public void setTurns(List<UUID> turns) {
        this.turns = turns;
    }

    public Candidate surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
    	return surname;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        if (candidate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), candidate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Candidate{" +
            "id=" + getId() +
            ", age='" + getAge() + "'" +
            ", municipalityId='" + getMunicipalityId() + "'" +
            ", name='" + getName() + "'" +
            ", occupation='" + getOccupation() + "'" +
            ", partyId = '" + getPartyId() + "'" +
            ", surname='" + getSurname() +
            "}";
    }
}
