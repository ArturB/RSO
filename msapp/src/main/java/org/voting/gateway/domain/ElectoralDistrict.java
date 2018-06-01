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

@Table(name = "electoral_district",keyspace = "rso",
    caseSensitiveKeyspace = false,
    caseSensitiveTable = false)
public class ElectoralDistrict implements Serializable {

    private static final long serialVersionUID = 1L;


    @PartitionKey

    @Column(name = "electoral_district_id")
    private UUID id;

    @Column(name = "municipality_id")
    private UUID municipalityId;

    @Column(name = "electoral_district_name")
    private String electoralDistrictName;

    public UUID getId() {
        return id;
    }

    public ElectoralDistrict id(UUID id) {
        this.id = id;
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
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
            ", municipalityId='" + getMunicipalityId() + "'" +
            ", electoralDistrictName='" + getElectoralDistrictName() +
            "}";
    }
}
