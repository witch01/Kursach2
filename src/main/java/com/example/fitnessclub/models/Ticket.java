package com.example.fitnessclub.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.sql.Date;

@Entity
public class Ticket {
    public  Ticket(){

    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @PastOrPresent(message = "Указана неверная дата")
    private Date date_ticket;

    @NotEmpty(message = "Поле не может быть пустым")
    private double cent;
    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name="client_id")
    private Client client;
    @OneToOne(optional = true, mappedBy = "ticket")
    private Cheque cheque;

    public Ticket(Date date_ticket, double cent, Client client, Cheque cheque) {
        this.date_ticket = date_ticket;
        this.cent = cent;
        this.client = client;
        this.cheque = cheque;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate_ticket() {
        return date_ticket;
    }

    public void setDate_ticket(Date date_ticket) {
        this.date_ticket = date_ticket;
    }

    public double getCent() {
        return cent;
    }

    public void setCent(double cent) {
        this.cent = cent;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Cheque getCheque() {
        return cheque;
    }

    public void setCheque(Cheque cheque) {
        this.cheque = cheque;
    }
}
