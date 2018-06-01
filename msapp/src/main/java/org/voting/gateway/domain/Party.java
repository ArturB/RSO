package org.voting.gateway.domain;





import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A Party.
 */

@Table(name = "party",
    keyspace = "rso",
    caseSensitiveKeyspace = false,
    caseSensitiveTable = false)
public class Party implements Serializable {

    private static final long serialVersionUID = 1L;



    @PartitionKey
    @Column(name = "party_id")
    private UUID id;

    @Column(name = "party_abbreviation")
    private String abbreviation;

    @Column(name = "party_name")
    private String name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Party id(UUID id) {
    	this.id = id;
    	return this;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public Party abbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
        return this;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public Party name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
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
            ", abbreviation=" + getAbbreviation() + "'" +
            ", name='" + getName() +
            "}";
    }
}
