package org.voting.gateway.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A VotesFromDistrict.
 */
@Entity
@Table(name = "votes_from_district")
public class VotesFromDistrict implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "nr_of_votes", nullable = false)
    private Integer nrOfVotes;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "jhi_type", nullable = false)
    private String type;

    @ManyToOne
    private ElectoralDistrict electoralDistrict;

    @ManyToOne
    private Candidate candidate;

    @ManyToOne
    private MyUser user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNrOfVotes() {
        return nrOfVotes;
    }

    public VotesFromDistrict nrOfVotes(Integer nrOfVotes) {
        this.nrOfVotes = nrOfVotes;
        return this;
    }

    public void setNrOfVotes(Integer nrOfVotes) {
        this.nrOfVotes = nrOfVotes;
    }

    public LocalDate getDate() {
        return date;
    }

    public VotesFromDistrict date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public VotesFromDistrict type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ElectoralDistrict getElectoralDistrict() {
        return electoralDistrict;
    }

    public VotesFromDistrict electoralDistrict(ElectoralDistrict electoralDistrict) {
        this.electoralDistrict = electoralDistrict;
        return this;
    }

    public void setElectoralDistrict(ElectoralDistrict electoralDistrict) {
        this.electoralDistrict = electoralDistrict;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public VotesFromDistrict candidate(Candidate candidate) {
        this.candidate = candidate;
        return this;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public MyUser getUser() {
        return user;
    }

    public VotesFromDistrict user(MyUser myUser) {
        this.user = myUser;
        return this;
    }

    public void setUser(MyUser myUser) {
        this.user = myUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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
            "id=" + getId() +
            ", nrOfVotes=" + getNrOfVotes() +
            ", date='" + getDate() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
