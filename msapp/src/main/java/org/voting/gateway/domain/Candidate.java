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
    private UUID municipality_id;

    @Column(name = "party")
    private UUID party_id;

    @Column(name = "turns")
    private List<UUID> turns;






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

    public UUID getCandidate_id() {
        return candidate_id;
    }

    public void setCandidate_id(UUID candidate_id) {
        this.candidate_id = candidate_id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public UUID getMunicipality_id() {
        return municipality_id;
    }

    public void setMunicipality_id(UUID municipality_id) {
        this.municipality_id = municipality_id;
    }

    public UUID getParty_id() {
        return party_id;
    }

    public void setParty_id(UUID party_id) {
        this.party_id = party_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}
