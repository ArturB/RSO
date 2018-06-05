package org.voting.gateway.service;

import java.util.UUID;

public class SmallUserDTO {

	private UUID id;
	private String username;
	private UUID municipalityId;
	private UUID electoralDistrictId;
	private String role;
	
	public SmallUserDTO(UUID id, String username, UUID municipalityId, UUID electoralDistrictId, String role)
	{
		this.id = id;
		this.username = username;
		this.municipalityId = municipalityId;
		this.electoralDistrictId = electoralDistrictId;
		this.role = role;
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
	
	
}
