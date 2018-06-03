package org.voting.gateway.domain;



import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * A SmallUser.
 */

@Table(name = "user",keyspace = "rso",
    caseSensitiveKeyspace = false,
    caseSensitiveTable = false)
public class SmallUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    @Column(name = "user_id")
    private UUID id;

    @Column(name = "commune")
    private UUID municipality_id;

    @Column(name = "ward")
    private UUID electoral_district_id;

    @Column(name = "username")
    private String username;

    @Column(name = "group")
    private String role;

    @Column(name = "is_blocked")
    private boolean disabled;

    //private Set<String> authorities;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public SmallUser id(UUID id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public SmallUser username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public SmallUser role(String role) {
        this.role = role;
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UUID getMunicipalityId() {
        return municipality_id;
    }

    public void setMunicipalityId(UUID municipalityId) {
        this.municipality_id = municipalityId;
    }

    public UUID getElectoralDistrictId() {
        return electoral_district_id;
    }

    public void setElectoralDistrictId(UUID electoralDistrictId) {
        this.electoral_district_id = electoralDistrictId;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    /*
    public Set<String> getAuthorities() {
        return authorities;
    }


    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public SmallUser authorities(Set<String> authorities) {
        this.authorities = authorities;
        return this;
    }
	*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmallUser smallUser = (SmallUser) o;

        if (id != null ? !id.equals(smallUser.id) : smallUser.id != null) return false;
        if (username != null ? !username.equals(smallUser.username) : smallUser.username != null) return false;
        if (electoral_district_id != null ? !electoral_district_id.equals(smallUser.electoral_district_id) : smallUser.electoral_district_id != null)
            return false;
        if (municipality_id != null ? !municipality_id.equals(smallUser.municipality_id) : smallUser.municipality_id != null)
            return false;
        if (role != null ? !role.equals(smallUser.role) : smallUser.role != null) return false;
        //return authorities != null ? authorities.equals(smallUser.authorities) : smallUser.authorities == null;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (electoral_district_id != null ? electoral_district_id.hashCode() : 0);
        result = 31 * result + (municipality_id != null ? municipality_id.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        //result = 31 * result + (authorities != null ? authorities.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SmallUser{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", electoralDistrictId=" + electoral_district_id +
            ", municipalityId=" + municipality_id +
            ", role='" + role + //'\'' +
            //", authorities=" + authorities +
            '}';
    }
}
