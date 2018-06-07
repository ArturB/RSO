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
}
