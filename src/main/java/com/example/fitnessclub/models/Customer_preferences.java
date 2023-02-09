package com.example.fitnessclub.models;

import jakarta.persistence.*;

import javax.validation.constraints.NotEmpty;

@Entity
public class Customer_preferences {
    public Customer_preferences()
    {}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Поле не может быть пустым")
    private String preferences;
    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name="client_id")
    private Client client;

    public Customer_preferences(String preferences, Client client) {
        this.preferences = preferences;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
