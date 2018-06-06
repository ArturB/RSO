package org.voting.gateway.domain;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.UUID;

import org.voting.gateway.service.RodoUserDTO;
import org.voting.gateway.service.UserDTO;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;


@Table(name = "user",keyspace = "rodo",
caseSensitiveKeyspace = false,
caseSensitiveTable = false)
public class RodoUser implements Serializable{

    private static final long serialVersionUID = 1L;

    @PartitionKey
    @Column(name = "user_id")
    private UUID id;

    @Column(name = "birthdate")
    private ByteBuffer birthdate;

    @Column(name = "document_no")
    private ByteBuffer document_no;
    
    @Column(name = "document_type")
    private ByteBuffer document_type;
    
    @Column(name = "email")
    private ByteBuffer email;
    
    @Column(name = "name")
    private ByteBuffer name;
    
    @Column(name = "pesel")
    private ByteBuffer pesel;
    
    @Column(name = "surname")
    private ByteBuffer surname;

    public RodoUser(RodoUserDTO user) {
    	
    	// TODO zrobic szyfrowanie
    	//this.id = user.getId();
    	//this.birthdate = user.getBirthdate();
    	//this.document_no = user.getDocumentNr();
    	//this.document_type = user.getDocumentType();
    	//this.email = 
    }
    
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public ByteBuffer getBirthdate_id() {
		return birthdate;
	}

	public void setMunicipality_id(ByteBuffer birthdate) {
		this.birthdate = birthdate;
	}

	public ByteBuffer getDocument_no() {
		return document_no;
	}

	public void setDocument_no(ByteBuffer document_no) {
		this.document_no = document_no;
	}

	public ByteBuffer getDocument_type() {
		return document_type;
	}

	public void setDocument_type(ByteBuffer document_type) {
		this.document_type = document_type;
	}

	public ByteBuffer getEmail() {
		return email;
	}

	public void setEmail(ByteBuffer email) {
		this.email = email;
	}

	public ByteBuffer getName() {
		return name;
	}

	public void setName(ByteBuffer name) {
		this.name = name;
	}

	public ByteBuffer getPesel() {
		return pesel;
	}

	public void setPesel(ByteBuffer pesel) {
		this.pesel = pesel;
	}

	public ByteBuffer getSurname() {
		return surname;
	}

	public void setSurname(ByteBuffer surname) {
		this.surname = surname;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    
}
