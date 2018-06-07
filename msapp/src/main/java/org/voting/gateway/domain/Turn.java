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
    private UUID commune;

    @Column(name = "date_from")
    private Date dateFrom;

    @Column(name = "date_to")
    private Date dateTo;



    @Column(name = "is_last_turn")
    private boolean lastTurn;


    @Column(name = "is_turn_finished")
    private boolean turnFinished;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

    public UUID getCommune() {
        return commune;
    }

    public void setCommune(UUID commune) {
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


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    public boolean isLastTurn() {
        return lastTurn;
    }

    public void setLastTurn(boolean lastTurn) {
        this.lastTurn = lastTurn;
    }

    public boolean isTurnFinished() {
        return turnFinished;
    }

    public void setTurnFinished(boolean turnFinished) {
        this.turnFinished = turnFinished;
    }
}
