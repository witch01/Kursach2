package com.example.fitnessclub.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Time;

@Entity
public class Cheque {
    public Cheque(){}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @PastOrPresent(message = "Указана неверная дата")
    private Date date_cheque;
    private Time time_cheque;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name="ticket_id")
    private Ticket ticket;

    public Cheque(Date date_cheque, Time time_cheque, Ticket ticket) {
        this.date_cheque = date_cheque;
        this.time_cheque = time_cheque;
        this.ticket = ticket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate_cheque() {
        return date_cheque;
    }

    public void setDate_cheque(Date date_cheque) {
        this.date_cheque = date_cheque;
    }

    public Time getTime_cheque() {
        return time_cheque;
    }

    public void setTime_cheque(Time time_cheque) {
        this.time_cheque = time_cheque;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
