package org.voting.gateway.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.datastax.driver.mapping.annotations.PartitionKey;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A Municipality.
 */
@Entity
@Table(name = "municipality")
public class Municipality implements Serializable {

    private static final long serialVersionUID = 1L;

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id;

    @PartitionKey
    @NotNull
    @Column(name = "municipality_id")
    private UUID id;    
    
    @Column(name = "municipality_name")
    private String name;

    @Column(name = "municipality_position_name")
    private String positionName;
    
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Municipality id(UUID id) {
    	this.id = id;
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
        if (municipality.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), municipality.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Municipality{" +
            "id=" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", positionName='" + getPositionName() +
            "}";
    }
}
