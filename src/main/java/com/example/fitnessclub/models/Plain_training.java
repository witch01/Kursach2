package com.example.fitnessclub.models;

import jakarta.persistence.*;

import javax.validation.constraints.NotEmpty;

@Entity
public class Plain_training {
    public  Plain_training(){}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Поле не может быть пустым")
    private String plain;
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Employee employee;
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Client client;

    public Plain_training(String plain, Employee employee, Client client) {
        this.plain = plain;
        this.employee = employee;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlain() {
        return plain;
    }

    public void setPlain(String plain) {
        this.plain = plain;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
