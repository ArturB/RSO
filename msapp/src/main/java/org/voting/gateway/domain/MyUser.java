package org.voting.gateway.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A MyUser.
 */
@Entity
@Table(name = "my_user")
public class MyUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "surname", nullable = false)
    private String surname;

    @NotNull
    @Column(name = "document_type", nullable = false)
    private String documentType;

    @NotNull
    @Column(name = "document_nr", nullable = false)
    private String documentNr;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @NotNull
    @Size(min = 11, max = 11)
    @Column(name = "pesel", length = 11, nullable = false)
    private String pesel;

    @ManyToOne
    private MyGroup myGroup;

    @ManyToOne
    private ElectoralDistrict electoralDistrict;

    @ManyToOne
    private Municipality municipality;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public MyUser username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public MyUser name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public MyUser surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDocumentType() {
        return documentType;
    }

    public MyUser documentType(String documentType) {
        this.documentType = documentType;
        return this;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNr() {
        return documentNr;
    }

    public MyUser documentNr(String documentNr) {
        this.documentNr = documentNr;
        return this;
    }

    public void setDocumentNr(String documentNr) {
        this.documentNr = documentNr;
    }

    public String getEmail() {
        return email;
    }

    public MyUser email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public MyUser birthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getPesel() {
        return pesel;
    }

    public MyUser pesel(String pesel) {
        this.pesel = pesel;
        return this;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public MyGroup getMyGroup() {
        return myGroup;
    }

    public MyUser myGroup(MyGroup myGroup) {
        this.myGroup = myGroup;
        return this;
    }

    public void setMyGroup(MyGroup myGroup) {
        this.myGroup = myGroup;
    }

    public ElectoralDistrict getElectoralDistrict() {
        return electoralDistrict;
    }

    public MyUser electoralDistrict(ElectoralDistrict electoralDistrict) {
        this.electoralDistrict = electoralDistrict;
        return this;
    }

    public void setElectoralDistrict(ElectoralDistrict electoralDistrict) {
        this.electoralDistrict = electoralDistrict;
    }

    public Municipality getMunicipality() {
        return municipality;
    }

    public MyUser municipality(Municipality municipality) {
        this.municipality = municipality;
        return this;
    }

    public void setMunicipality(Municipality municipality) {
        this.municipality = municipality;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MyUser myUser = (MyUser) o;
        if (myUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), myUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MyUser{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", documentType='" + getDocumentType() + "'" +
            ", documentNr='" + getDocumentNr() + "'" +
            ", email='" + getEmail() + "'" +
            ", birthdate='" + getBirthdate() + "'" +
            ", pesel='" + getPesel() + "'" +
            "}";
    }
}
