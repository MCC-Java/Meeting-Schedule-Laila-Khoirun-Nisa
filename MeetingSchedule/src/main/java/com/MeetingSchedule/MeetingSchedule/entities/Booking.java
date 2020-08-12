/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MeetingSchedule.MeetingSchedule.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Laila
 */
@Entity
@Table(name = "booking")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Booking.findAll", query = "SELECT b FROM Booking b")
    , @NamedQuery(name = "Booking.findById", query = "SELECT b FROM Booking b WHERE b.id = :id")
    , @NamedQuery(name = "Booking.findByName", query = "SELECT b FROM Booking b WHERE b.name = :name")
    , @NamedQuery(name = "Booking.findByTime", query = "SELECT b FROM Booking b WHERE b.time = :time")
    , @NamedQuery(name = "Booking.findByDatestart", query = "SELECT b FROM Booking b WHERE b.datestart = :datestart")
    , @NamedQuery(name = "Booking.findByDatenow", query = "SELECT b FROM Booking b WHERE b.datenow = :datenow")
    , @NamedQuery(name = "Booking.findByDateend", query = "SELECT b FROM Booking b WHERE b.dateend = :dateend")
    , @NamedQuery(name = "Booking.findByNoteapproval", query = "SELECT b FROM Booking b WHERE b.noteapproval = :noteapproval")
    , @NamedQuery(name = "Booking.findByAlternativeroom", query = "SELECT b FROM Booking b WHERE b.alternativeroom = :alternativeroom")})
public class Booking implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "time")
    private String time;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "datestart")
    private String datestart;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "dateend")
    private String dateend;
    @Size(max = 100)
    @Column(name = "noteapproval")
    private String noteapproval;
    @Size(max = 45)
    @Column(name = "alternativeroom")
    private String alternativeroom;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "id")
    private String id;
    @Column(name = "datenow")
    private String datenow;
    @Column(name = "kodeapproval")
//    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private int kodeapproval;
    @Column(name = "employeeid")
//    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private String employeeid;
    @Column(name = "room")
//    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private String room;

    public Booking() {
    }

    public Booking(String id) {
        this.id = id;
    }

    public Booking(String id, String name, String time, String datestart, String datenow, String dateend) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.datestart = datestart;
        this.datenow = datenow;
        this.dateend = dateend;
    }
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public int getKodeapproval() {
        return kodeapproval;
    }

    public void setKodeapproval(int kodeapproval) {
        this.kodeapproval = kodeapproval;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Booking)) {
            return false;
        }
        Booking other = (Booking) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + id + "";
        
    }

    /**
     * @return the datenow
     */
    public String getDatenow() {
        return datenow;
    }

    /**
     * @param datenow the datenow to set
     */
    public void setDatenow(String datenow) {
        this.datenow = datenow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDatestart() {
        return datestart;
    }

    public void setDatestart(String datestart) {
        this.datestart = datestart;
    }

    public String getDateend() {
        return dateend;
    }

    public void setDateend(String dateend) {
        this.dateend = dateend;
    }

    public String getNoteapproval() {
        return noteapproval;
    }

    public void setNoteapproval(String noteapproval) {
        this.noteapproval = noteapproval;
    }

    public String getAlternativeroom() {
        return alternativeroom;
    }

    public void setAlternativeroom(String alternativeroom) {
        this.alternativeroom = alternativeroom;
    }
    
    
}
