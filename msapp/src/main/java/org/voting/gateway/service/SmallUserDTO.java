package org.voting.gateway.service;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.UUID;

public class SmallUserDTO implements Serializable {

	private UUID id;
	private String username;
	private UUID municipalityId;
	private UUID electoralDistrictId;
	private String role;
	private boolean disabled;
    private Set<String> authorities;

	public SmallUserDTO(UUID id, String username, UUID municipalityId, UUID electoralDistrictId, String role, boolean disabled)
	{
		this.id = id;
		this.username = username;
		this.municipalityId = municipalityId;
		this.electoralDistrictId = electoralDistrictId;
		this.role = role;
        this.disabled = disabled;
        this.authorities = new HashSet<>();
		this.authorities.add(role);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UUID getMunicipalityId() {
		return municipalityId;
	}

	public void setMunicipalityId(UUID municipalityId) {
		this.municipalityId = municipalityId;
	}

	public UUID getElectoralDistrictId() {
		return electoralDistrictId;
	}

	public void setElectoralDistrictId(UUID electoralDistrictId) {
		this.electoralDistrictId = electoralDistrictId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
