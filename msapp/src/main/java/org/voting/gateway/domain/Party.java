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
    private String party_abbreviation;

    @Column(name = "party_name")
    private String party_name;

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

    public String getParty_abbreviation() {
        return party_abbreviation;
    }

    public Party abbreviation(String abbreviation) {
        this.party_abbreviation = abbreviation;
        return this;
    }

    public void setParty_abbreviation(String party_abbreviation) {
        this.party_abbreviation = party_abbreviation;
    }

    public String getParty_name() {
        return party_name;
    }

    public Party name(String name) {
        this.party_name = name;
        return this;
    }

    public void setParty_name(String party_name) {
        this.party_name = party_name;
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
        if (municipality.getMunicipality_id() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), municipality.getMunicipality_id());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Municipality{" +
            "id=" + getId() + "'" +
            ", abbreviation=" + getParty_abbreviation() + "'" +
            ", name='" + getParty_name() +
            "}";
    }
}
