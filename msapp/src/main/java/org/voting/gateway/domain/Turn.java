package org.voting.gateway.domain;

import java.util.Date;
import java.util.UUID;

import com.datastax.driver.core.LocalDate;
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
    private LocalDate dateFrom;

    @Column(name = "date_to")
    private LocalDate dateTo;

    @Column(name = "no_turn")
    private Integer noTurn;

    @Column(name = "is_last_turn")
    private boolean isLastTurn;


    @Column(name = "is_turn_finished")
    private boolean isTurnFinished;

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

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
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

    public boolean isLastTurn() {
        return isLastTurn;
    }

    public void setLastTurn(boolean lastTurn) {
        isLastTurn = lastTurn;
    }

    public boolean isTurnFinished() {
        return isTurnFinished;
    }

    public void setTurnFinished(boolean turnFinished) {
        isTurnFinished = turnFinished;
    }
}
