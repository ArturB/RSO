package org.voting.gateway.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ElectoralDistrict.
 */
@Entity
@Table(name = "electoral_district")
public class ElectoralDistrict implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "nr_can_vote", nullable = false)
    private Integer nrCanVote;

    @NotNull
    @Column(name = "nr_cards_used", nullable = false)
    private Integer nrCardsUsed;

    @NotNull
    @Column(name = "voting_finished", nullable = false)
    private Boolean votingFinished;

    @ManyToOne
    @NotNull
    private Municipality municipality;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ElectoralDistrict name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNrCanVote() {
        return nrCanVote;
    }

    public ElectoralDistrict nrCanVote(Integer nrCanVote) {
        this.nrCanVote = nrCanVote;
        return this;
    }

    public void setNrCanVote(Integer nrCanVote) {
        this.nrCanVote = nrCanVote;
    }

    public Integer getNrCardsUsed() {
        return nrCardsUsed;
    }

    public ElectoralDistrict nrCardsUsed(Integer nrCardsUsed) {
        this.nrCardsUsed = nrCardsUsed;
        return this;
    }

    public void setNrCardsUsed(Integer nrCardsUsed) {
        this.nrCardsUsed = nrCardsUsed;
    }

    public Boolean isVotingFinished() {
        return votingFinished;
    }

    public ElectoralDistrict votingFinished(Boolean votingFinished) {
        this.votingFinished = votingFinished;
        return this;
    }

    public void setVotingFinished(Boolean votingFinished) {
        this.votingFinished = votingFinished;
    }

    public Municipality getMunicipality() {
        return municipality;
    }

    public ElectoralDistrict municipality(Municipality municipality) {
        this.municipality = municipality;
        return this;
    }

    public void setMunicipality(Municipality municipality) {
        this.municipality = municipality;
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
        ElectoralDistrict electoralDistrict = (ElectoralDistrict) o;
        if (electoralDistrict.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), electoralDistrict.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ElectoralDistrict{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", nrCanVote=" + getNrCanVote() +
            ", nrCardsUsed=" + getNrCardsUsed() +
            ", votingFinished='" + isVotingFinished() + "'" +
            "}";
    }
}
