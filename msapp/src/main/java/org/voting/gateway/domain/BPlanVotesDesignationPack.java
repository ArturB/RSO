package org.voting.gateway.domain;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by defacto on 6/8/2018.
 */

@Table(name = "bPlanVotesDesignationPack",
    keyspace = "rso",
    caseSensitiveKeyspace = false,
    caseSensitiveTable = false)
public class BPlanVotesDesignationPack implements Serializable{

    private static final long serialVersionUID = 1L;

    @PartitionKey
    @Column(name = "pack_id")
    private UUID pack_id;

    @Column(name = "position_name")
    private String positionName;

    @Column(name = "date")
    private Date date;

    @Column(name = "electoral_district_id")
    private UUID electoral_district_id;

    @Column(name = "user_id")
    private UUID user_id;

    @Column(name = "too_many_marks_cards_used")
    private int too_many_marks_cards_used;

    @Column(name = "erased_marks_cards_used")
    private int none_marks_cards_used;

    @Column(name = "erased_marks_cards_used")
    private int erased_marks_cards_used;

    @Column(name = "round")
    private int round;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public UUID getPack_id() {
        return pack_id;
    }

    public void setPack_id(UUID pack_id) {
        this.pack_id = pack_id;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UUID getElectoral_district_id() {
        return electoral_district_id;
    }

    public void setElectoral_district_id(UUID electoral_district_id) {
        this.electoral_district_id = electoral_district_id;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public int getToo_many_marks_cards_used() {
        return too_many_marks_cards_used;
    }

    public void setToo_many_marks_cards_used(int too_many_marks_cards_used) {
        this.too_many_marks_cards_used = too_many_marks_cards_used;
    }

    public int getNone_marks_cards_used() {
        return none_marks_cards_used;
    }

    public void setNone_marks_cards_used(int none_marks_cards_used) {
        this.none_marks_cards_used = none_marks_cards_used;
    }

    public int getErased_marks_cards_used() {
        return erased_marks_cards_used;
    }

    public void setErased_marks_cards_used(int erased_marks_cards_used) {
        this.erased_marks_cards_used = erased_marks_cards_used;
    }

    public int getRound() {
        return round;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BPlanVotesDesignationPack that = (BPlanVotesDesignationPack) o;

        return pack_id != null ? pack_id.equals(that.pack_id) : that.pack_id == null;
    }

    @Override
    public int hashCode() {
        return pack_id != null ? pack_id.hashCode() : 0;
    }

    public void setRound(int round)

    {
        this.round = round;
    }
}
