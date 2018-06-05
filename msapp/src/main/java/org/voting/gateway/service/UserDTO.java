package org.voting.gateway.service;

import java.util.Date;
import java.util.UUID;

public class UserDTO {

    private UUID id;
    private String username;
    private String name;
    private String surname;
    private String documentType;
    private String documentNr;
    private String email;
    private Date birthdate;
    private String pesel;
    private UUID electoralDistrictId;
    private UUID municipalityId;
    private String role;
    
    private String password;
    
    public UserDTO(UUID id, String username, String name, String surname,
    		String documentType, String documentNr, String email, Date birthdate,
    		String pesel, UUID electoralDistrictId, String role)
    {
    	this.id = id;
    	this.username = username;
    	this.name = name;
    	this.surname = name;
    	this.documentType = documentType;
    	this.documentNr = documentNr;
    	this.email = email;
    	this.birthdate = birthdate;
    	this.pesel = pesel;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentNr() {
		return documentNr;
	}

	public void setDocumentNr(String documentNr) {
		this.documentNr = documentNr;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	public UUID getElectoralDistrictId() {
		return electoralDistrictId;
	}

	public void setElectoralDistrictId(UUID electoralDistrictId) {
		this.electoralDistrictId = electoralDistrictId;
	}

	public UUID getMunicipalityId() {
		return municipalityId;
	}

	public void setMunicipalityId(UUID municipalityId) {
		this.municipalityId = municipalityId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    
    
	// TODO
	/*
	 * Stworzyc userdto i na podstawie tego czy aktualnie zalogowany uzytkownik to admin
	 * to albo wypelniac haslo z bazy albo wstawiac nulla
	 */
}
