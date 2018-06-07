package org.voting.gateway.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DuxxEntity.
 */
@Entity
@Table(name = "duxx_entity")
public class DuxxEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "field_1")
    private String field1;

    @Column(name = "field_2")
    private Long field2;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getField1() {
        return field1;
    }

    public DuxxEntity field1(String field1) {
        this.field1 = field1;
        return this;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public Long getField2() {
        return field2;
    }

    public DuxxEntity field2(Long field2) {
        this.field2 = field2;
        return this;
    }

    public void setField2(Long field2) {
        this.field2 = field2;
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
        DuxxEntity duxxEntity = (DuxxEntity) o;
        if (duxxEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), duxxEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DuxxEntity{" +
            "id=" + getId() +
            ", field1='" + getField1() + "'" +
            ", field2=" + getField2() +
            "}";
    }
}
