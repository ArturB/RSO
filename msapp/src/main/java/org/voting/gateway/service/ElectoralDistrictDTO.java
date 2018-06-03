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
}
