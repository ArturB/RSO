package org.voting.gateway.service;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;

import org.voting.gateway.domain.RodoUser;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;

public class RodoUserDTO {

    private UUID id;
    private Date birthdate;
    private String documentNo;
    private String documentType;
    private String email;    
    private String name;
    private String pesel;
    private String surname;
    
    public RodoUserDTO(UserDTO user) {
    	this.id = user.getId();
    	this.birthdate = user.getBirthdate();
    	this.documentNo = user.getDocumentNr();
    	this.documentType = user.getDocumentType();
    	this.email = user.getEmail();
    	this.name = user.getName();
    	this.pesel = user.getPesel();
    	this.surname = user.getSurname();
    }
    
    public RodoUserDTO (RodoUser rodoUser) {
    	// TODO zrobic deszyfrowanie tego co otrzymujemy i wstawianie w odpowiednie pola
    }

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getMunicipalityId() {
		return municipalityId;
	}

	public void setMunicipalityId(UUID municipalityId) {
		this.municipalityId = municipalityId;
	}

	public int getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(int documentNo) {
		this.documentNo = documentNo;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
    
    
}
