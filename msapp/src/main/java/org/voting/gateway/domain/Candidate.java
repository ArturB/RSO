package org.voting.gateway.domain;


//import javax.persistence.*;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A Candidate.
 */
//@Entity
@Table(name = "candidate",keyspace = "rso",
    readConsistency = "QUORUM",
    writeConsistency = "QUORUM",
    caseSensitiveKeyspace = false,
    caseSensitiveTable = false)
public class Candidate implements Serializable {

    private static final long serialVersionUID = 1L;

    //@Id
    @PartitionKey
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "candidate_id")
    private UUID id;    
    
    //@NotNull
    //@Min(value = 18)
    //@Column(name = "age", nullable = false)
    @Column(name = "age")
    private Short age;
    
    @NotNull
    @Column(name = "municipality_id")
    private Short municipalityId;
    
    // @NotNull
    // @Column(name = "name", nullable = false)
    @Column(name = "name")
    private String name;

    @Column(name = "occupation")
    private String occupation;
    
    @NotNull
    @Column(name = "party_id")
    private Short partyId;
    
    @Column(name = "round")
    private Short round;
    
    //@NotNull
    //@Column(name = "surname", nullable = false)
    @Column(name = "surname")
    private String surname;

    

    //@ManyToOne
    //private Party party;

    //@ManyToOne
    //private Municipality municipality;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
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
    
    public Short getManicipalityId() {
        return manicipalityId;
    }

    public Candidate municipalityId(Short municipalityId) {
        this.municipalityId = municipalityId;
        return this;
    }

    public void setMunicipalityId(Short municipalityId {
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
    
    public Short getRound() {
        return round;
    }

    public Candidate round(Short round) {
        this.round = round;
        return this;
    }

    public void setRound(Short round) {
        this.round = round;
    }
    
    public Short getRound() {
        return round;
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

//    public Party getParty() {
//        return party;
//    }

//    public Candidate party(Party party) {
//        this.party = party;
//        return this;
//    }

//    public void setParty(Party party) {
//        this.party = party;
//    }
//
//    public Municipality getMunicipality() {
//        return municipality;
//    }
//
//    public Candidate municipality(Municipality municipality) {
//        this.municipality = municipality;
//        return this;
//    }
//
//    public void setMunicipality(Municipality municipality) {
//        this.municipality = municipality;
//    }
//    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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
            ", round = '" + getRound() + "'" +
            ", surname='" + getSurname() +
            "}";
    }
}
