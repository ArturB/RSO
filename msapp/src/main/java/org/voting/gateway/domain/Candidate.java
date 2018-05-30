package org.voting.gateway.domain;


//import javax.persistence.*;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

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
    @Column(name = "cantdidate_id")
    private Integer id;

    @NotNull
   // @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    //@Column(name = "surname", nullable = false)
    private String surname;

    @NotNull
    //@Min(value = 18)
    //@Column(name = "age", nullable = false)
    private Short age;

    //@ManyToOne
    //private Party party;

    //@ManyToOne
    //private Municipality municipality;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSurname() {
        return surname;
    }

    public Candidate surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", age=" + getAge() +
            "}";
    }
}
