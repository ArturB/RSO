package org.voting.gateway.service;

import org.voting.gateway.domain.ElectoralDistrict;

import java.io.Serializable;
import java.util.UUID;

public class ElectoralDistrictDTO implements Serializable {

    private UUID electoral_district_id;
    private UUID municipality_id;
    private String electoral_district_name;
    private boolean first_round_votes_accepted;
    private boolean second_round_votes_accepted;

    public ElectoralDistrictDTO() {
    }

    public ElectoralDistrictDTO(ElectoralDistrict electoralDistrict) {

        this.electoral_district_id = electoralDistrict.getId();
        this.municipality_id = electoralDistrict.getMunicipalityId();
        this.electoral_district_name = electoralDistrict.getElectoralDistrictName();

    }

    public UUID getElectoral_district_id() {
        return electoral_district_id;
    }

    public void setElectoral_district_id(UUID electoral_district_id) {
        this.electoral_district_id = electoral_district_id;
    }

    public UUID getMunicipality_id() {
        return municipality_id;
    }

    public void setMunicipality_id(UUID municipality_id) {
        this.municipality_id = municipality_id;
    }

    public String getElectoral_district_name() {
        return electoral_district_name;
    }

    public void setElectoral_district_name(String electoral_district_name) {
        this.electoral_district_name = electoral_district_name;
    }

    public boolean isFirst_round_votes_accepted() {
        return first_round_votes_accepted;
    }

    public void setFirst_round_votes_accepted(boolean first_round_votes_accepted) {
        this.first_round_votes_accepted = first_round_votes_accepted;
    }

    public boolean isSecond_round_votes_accepted() {
        return second_round_votes_accepted;
    }

    public void setSecond_round_votes_accepted(boolean second_round_votes_accepted) {
        this.second_round_votes_accepted = second_round_votes_accepted;
    }
}
