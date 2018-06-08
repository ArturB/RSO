package org.voting.gateway.domain;




import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.datastax.driver.mapping.annotations.Transient;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;


/**
 * A Municipality.
 */

@Table(name = "commune",
    keyspace = "rso",
    caseSensitiveKeyspace = false,
    caseSensitiveTable = false)
public class Municipality implements Serializable {
    private static final long serialVersionUID = 1L;

    @PartitionKey
    @Column(name = "commune_id")
    private UUID municipality_id;

    @Column(name = "commune_name")
    private String name;

    @Column(name = "position_name")
    private String positionName;

    @Column(name = "first_round_completed")
    private Boolean first_turn_completed;

    @Column(name = "second_round_completed")
    private Boolean second_turn_completed;

    @Column(name = "has_second_round")
    private Boolean has_second_round;

    public UUID getMunicipality_id() {
        return municipality_id;
    }

    public void setMunicipality_id(UUID municipality_id) {
        this.municipality_id = municipality_id;
    }

    public Municipality id(UUID id) {
    	this.municipality_id = id;
    	return this;
    }

    public String getName() {
        return name;
    }

    public Municipality name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPositionName() {
        return positionName;
    }

    public Municipality positionName(String positionName) {
        this.positionName = positionName;
        return this;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Municipality municipality = (Municipality) o;
        if (municipality.getMunicipality_id() == null || getMunicipality_id() == null) {
            return false;
        }
        return Objects.equals(getMunicipality_id(), municipality.getMunicipality_id());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getMunicipality_id());
    }

    @Override
    public String toString() {
        return "Municipality{" +
            "id=" + getMunicipality_id() + "'" +
            ", name='" + getName() + "'" +
            ", positionName='" + getPositionName() +
            "}";
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

    public Boolean getHas_second_round() {
        return has_second_round;
    }

    public void setHas_second_round(Boolean has_second_round) {
        this.has_second_round = has_second_round;
    }
}
