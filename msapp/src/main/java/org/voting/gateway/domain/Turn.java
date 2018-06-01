package org.voting.gateway.domain;

import java.util.Date;
import java.util.UUID;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(name = "turn",keyspace = "rso",
caseSensitiveKeyspace = false,
caseSensitiveTable = false)
public class Turn {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    @Column(name = "turn_id")
    private UUID id;

    @Column(name = "commune")
    private Short commune;

    @Column(name = "date_from")
    private Date dateFrom;

    @Column(name = "date_to")
    private Date dateTo;

    @Column(name = "no_turn")
    private Integer noTurn;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Short getCommune() {
		return commune;
	}

	public void setCommune(Short commune) {
		this.commune = commune;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Integer getNoTurn() {
		return noTurn;
	}

	public void setNoTurn(Integer noTurn) {
		this.noTurn = noTurn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
