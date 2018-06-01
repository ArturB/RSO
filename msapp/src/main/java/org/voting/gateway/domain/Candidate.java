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
    private UUID id;


    @Column(name = "age")
    private Short age;


    @Column(name = "commune")
    private Short municipalityId;


    @Column(name = "name")
    private String name;

    @Column(name = "occupation")
    private String occupation;


    @Column(name = "party_id")
    private Short partyId;

    @Column(name = "surname")
    private String surname;

    @Column(name = "turns")
    private List<UUID> turns;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Candidate id(UUID id) {
    	this.id = id;
    	return this;
    }

    public Short getAge() {
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
        return municipalityId;
    }

    public Candidate municipalityId(Short municipalityId) {
        this.municipalityId = municipalityId;
        return this;
    }

    public void setMunicipalityId(Short municipalityId)
    {
        this.municipalityId = municipalityId;
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
        return partyId;
    }

    public Candidate partyId(Short partyId) {
        this.partyId = partyId;
        return this;
    }

    public void setPartyId(Short partyId) {
        this.partyId = partyId;
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
