package org.voting.gateway.service;

import org.voting.gateway.domain.Municipality;

import java.io.Serializable;
import java.util.UUID;

public class MunicipalityDTO implements Serializable {


    private UUID municipality_id;
    private String municipality_name;
    private String municipality_position_name;
    private boolean first_round_votes_accepted;
    private boolean second_round_votes_accepted;
    private boolean has_second_round;

    public MunicipalityDTO() {}

    public MunicipalityDTO(Municipality municipality)
    {
        this.municipality_id = municipality.getMunicipality_id();
        this.municipality_name = municipality.getName();
        this.municipality_position_name = municipality.getPositionName();
    }

    public UUID getMunicipality_id() {
        return municipality_id;
    }

    public void setMunicipality_id(UUID municipality_id) {
        this.municipality_id = municipality_id;
    }

    public String getMunicipality_name() {
        return municipality_name;
    }

    public void setMunicipality_name(String municipality_name) {
        this.municipality_name = municipality_name;
    }

    public String getMunicipality_position_name() {
        return municipality_position_name;
    }

    public void setMunicipality_position_name(String municipality_position_name) {
        this.municipality_position_name = municipality_position_name;
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

    public boolean isHas_second_round() {
        return has_second_round;
    }

    public void setHas_second_round(boolean has_second_round) {
        this.has_second_round = has_second_round;
    }
}
