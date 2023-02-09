package com.example.fitnessclub.models;

import jakarta.persistence.*;

@Entity
public class Recomended_services {
    public Recomended_services(){}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Services services;
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Client client;

    public Recomended_services(Services services, Client client) {
        this.services = services;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Services getServices() {
        return services;
    }

    public void setServices(Services services) {
        this.services = services;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
