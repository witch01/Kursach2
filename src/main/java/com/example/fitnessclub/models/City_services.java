package com.example.fitnessclub.models;

import jakarta.persistence.*;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class City_services {
    public City_services(){}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Поле не может быть пустым")
    private String name;

    @NotEmpty(message = "Поле не может быть пустым")
    private double cent;
    @OneToOne(optional = true, mappedBy = "city_services")
    private Services services;

    public City_services(String name, double cent, Services services) {
        this.name = name;
        this.cent = cent;
        this.services = services;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCent() {
        return cent;
    }

    public void setCent(double cent) {
        this.cent = cent;
    }

    public Services getServices() {
        return services;
    }

    public void setServices(Services services) {
        this.services = services;
    }
}
