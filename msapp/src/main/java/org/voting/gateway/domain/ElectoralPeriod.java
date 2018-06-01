package org.voting.gateway.domain;



import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.io.Serializable;
import java.util.Objects;


@Table(name = "electoral_district",
    keyspace = "rso",
    caseSensitiveKeyspace = false,
    caseSensitiveTable = false)
public class ElectoralPeriod implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey

    @Column(name = "name")
    private String name;

    @Column(name = "enddate")
    private String enddate;

    @Column(name = "startdate")
    private String startdate;

    public String getName() {
    	return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public ElectoralPeriod name(String name) {
    	this.name = name;
    	return this;
    }

    public String getEnddate() {
        return enddate;
    }

    public ElectoralPeriod enddate(String enddate) {
        this.enddate = enddate;
        return this;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getStartdate() {
        return startdate;
    }

    public ElectoralPeriod startdate(String startdate) {
        this.startdate = startdate;
        return this;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ElectoralPeriod electoralPeriod = (ElectoralPeriod) o;
        if (electoralPeriod.getName() == null || getName() == null) {
            return false;
        }
        return Objects.equals(getName(), electoralPeriod.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

    @Override
    public String toString() {
        return "ElectoralDistrict{" +
            "name=" + getName() + "'" +
            ", enddate=" + getEnddate() + "'" +
            ", startdate=" + getStartdate() +
            "}";
    }


}
