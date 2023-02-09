package com.example.fitnessclub.models;

import jakarta.persistence.*;

import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.List;

@Entity
public class Services {
    public Services(){}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Поле не может быть пустым")
    private double cent;
    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name="city_services_id")
    private City_services city_services;
    @OneToMany(mappedBy = "services", fetch = FetchType.EAGER)
    private Collection<Recomended_services> tenants;

    @ManyToMany
    @JoinTable (name="client_services",
            joinColumns=@JoinColumn (name="services_id"),
            inverseJoinColumns=@JoinColumn(name="client_id"))
    private List<Client> clients;

    public Services(double cent, City_services city_services, Collection<Recomended_services> tenants, List<Client> clients) {
        this.cent = cent;
        this.city_services = city_services;
        this.tenants = tenants;
        this.clients = clients;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getCent() {
        return cent;
    }

    public void setCent(double cent) {
        this.cent = cent;
    }

    public City_services getCity_services() {
        return city_services;
    }

    public void setCity_services(City_services city_services) {
        this.city_services = city_services;
    }

    public Collection<Recomended_services> getTenants() {
        return tenants;
    }

    public void setTenants(Collection<Recomended_services> tenants) {
        this.tenants = tenants;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}
