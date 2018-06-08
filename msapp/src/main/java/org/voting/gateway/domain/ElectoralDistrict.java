package org.voting.gateway.domain;





import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A ElectoralDistrict.
 */

@Table(name = "ward",keyspace = "rso",
    caseSensitiveKeyspace = false,
    caseSensitiveTable = false)
public class ElectoralDistrict implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    @Column(name = "ward_id")
    private UUID electoral_district_id;

    @Column(name = "commune")
    private UUID municipalityId;

    @Column(name = "ward_name")
    private String electoralDistrictName;

    @Column(name = "first_turn_no_can_vote")
    private int first_turn_no_can_vote ;

    @Column(name = "second_turn_no_can_vote")
    private int second_turn_no_can_vote ;

    @Column(name = "first_turn_no_cards_used")
    private int  first_turn_no_cards_used;

    @Column(name = "second_turn_no_cards_used")
    private int  second_turn_no_cards_used;

    @Column(name = "first_round_completed")
    private Boolean first_turn_completed;

    @Column(name = "second_round_completed")
    private Boolean second_turn_completed;

    public UUID getElectoral_district_id() {
        return electoral_district_id;
    }

    public ElectoralDistrict id(UUID id) {
        this.electoral_district_id = id;
        return this;
    }

    public void setElectoral_district_id(UUID electoral_district_id) {
        this.electoral_district_id = electoral_district_id;
    }

    public UUID getMunicipalityId() {
        return municipalityId;
    }

    public ElectoralDistrict municipalityId(UUID municipalityId) {
        this.municipalityId = municipalityId;
        return this;
    }

    public void setMunicipalityId(UUID municipalityId) {
        this.municipalityId = municipalityId;
    }

    public String getElectoralDistrictName() {
        return electoralDistrictName;
    }

    public ElectoralDistrict electoralDistrictName(String electoralDistrictName) {
        this.electoralDistrictName = electoralDistrictName;
        return this;
    }

    public void setElectoralDistrictName(String electoralDistrictName) {
        this.electoralDistrictName = electoralDistrictName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ElectoralDistrict electoralDistrict = (ElectoralDistrict) o;
        if (electoralDistrict.getElectoral_district_id() == null || getElectoral_district_id() == null) {
            return false;
        }
        return Objects.equals(getElectoral_district_id(), electoralDistrict.getElectoral_district_id());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getElectoral_district_id());
    }

    @Override
    public String toString() {
        return "ElectoralDistrict{" +
            "electoral_district_id=" + getElectoral_district_id() +
            ", municipalityId='" + getMunicipalityId() + "'" +
            ", electoralDistrictName='" + getElectoralDistrictName() +
            "}";
    }

    public int getFirst_turn_no_can_vote() {
        return first_turn_no_can_vote;
    }

    public void setFirst_turn_no_can_vote(int first_turn_no_can_vote) {
        this.first_turn_no_can_vote = first_turn_no_can_vote;
    }

    public int getSecond_turn_no_can_vote() {
        return second_turn_no_can_vote;
    }

    public void setSecond_turn_no_can_vote(int second_turn_no_can_vote) {
        this.second_turn_no_can_vote = second_turn_no_can_vote;
    }

    public int getFirst_turn_no_cards_used() {
        return first_turn_no_cards_used;
    }

    public void setFirst_turn_no_cards_used(int first_turn_no_cards_used) {
        this.first_turn_no_cards_used = first_turn_no_cards_used;
    }

    public int getSecond_turn_no_cards_used() {
        return second_turn_no_cards_used;
    }

    public void setSecond_turn_no_cards_used(int second_turn_no_cards_used) {
        this.second_turn_no_cards_used = second_turn_no_cards_used;
    }

    public Boolean getFirst_turn_completed() {
        return first_turn_completed;
    }

    public void setFirst_turn_completed(Boolean first_turn_completed) {
        this.first_turn_completed = first_turn_completed;
    }

    public Boolean getSecond_turn_completed() {
        return second_turn_completed;
    }

    public void setSecond_turn_completed(Boolean second_turn_completed) {
        this.second_turn_completed = second_turn_completed;
    }
}
