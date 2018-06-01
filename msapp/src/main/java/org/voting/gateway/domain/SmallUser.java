package org.voting.gateway.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.datastax.driver.mapping.annotations.PartitionKey;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * A SmallUser.
 */
public class SmallUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    @Column(name = "user_id")
    private UUID id;
    
    @Column(name = "commune")
    private Short municipalityId;
    
    @Column(name = "wart")
    private Short electoralDistrictId;
    
    @Column(name = "username")
    private String username;
    
    @Column(name = "group")
    private String role;
    
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

    public Short getElectoralDistrictId() {
        return electoralDistrictId;
    }

    public Short getMunicipalityId() {
        return municipalityId;
    }

    public void setElectoralDistrictId(Short electoralDistrictId) {
        this.electoralDistrictId = electoralDistrictId;
    }

    public void setMunicipalityId(Short municipalityId) {
        this.municipalityId = municipalityId;
    }

    public SmallUser electoralDistrictId(Short electoralDistrictId) {
        this.electoralDistrictId = electoralDistrictId;
        return this;
    }

    public SmallUser municipalityId(Short municipalityId) {
        this.municipalityId = municipalityId;
        return this;
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
        if (electoralDistrictId != null ? !electoralDistrictId.equals(smallUser.electoralDistrictId) : smallUser.electoralDistrictId != null)
            return false;
        if (municipalityId != null ? !municipalityId.equals(smallUser.municipalityId) : smallUser.municipalityId != null)
            return false;
        if (role != null ? !role.equals(smallUser.role) : smallUser.role != null) return false;
        //return authorities != null ? authorities.equals(smallUser.authorities) : smallUser.authorities == null;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (electoralDistrictId != null ? electoralDistrictId.hashCode() : 0);
        result = 31 * result + (municipalityId != null ? municipalityId.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        //result = 31 * result + (authorities != null ? authorities.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SmallUser{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", electoralDistrictId=" + electoralDistrictId +
            ", municipalityId=" + municipalityId +
            ", role='" + role + //'\'' +
            //", authorities=" + authorities +
            '}';
    }
}
