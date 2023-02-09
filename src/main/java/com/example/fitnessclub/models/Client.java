package com.example.fitnessclub.models;

import jakarta.persistence.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Entity
public class Client {
    public Client(){}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Digits(message="Номер должен содеражть 10 символов.", fraction = 0, integer = 10)
    private String phone;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min=2, max = 50, message = "Фамилия не можетт быть короче двух и длиннее 50 символов.")
    private String surname;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min=2, max = 50, message = "Имя не можетт быть короче двух и длиннее 50 символов.")
    private String name;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min=2, max = 50, message = "Отчество не можетт быть короче двух и длиннее 50 символов.")
    private String middle_name;

    @NotNull
    @Column(nullable = true)
    private boolean okeys = true;

    @ManyToMany
    @JoinTable(name="client_service",
            joinColumns=@JoinColumn(name="client_id"),
            inverseJoinColumns=@JoinColumn(name="service_id"))
    private List<Services> services;

    @OneToOne(optional = true, mappedBy = "client")
    private Customer_preferences customer_preferences;
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Collection<Plain_training> tenants;
    @OneToOne(optional = true, mappedBy = "client")
    private Ticket ticket;

    public Client(String phone, String surname, String name, String middle_name, boolean okeys, List<Services> services, Customer_preferences customer_preferences, Collection<Plain_training> tenants, Ticket ticket) {
        this.phone = phone;
        this.surname = surname;
        this.name = name;
        this.middle_name = middle_name;
        this.okeys = okeys;
        this.services = services;
        this.customer_preferences = customer_preferences;
        this.tenants = tenants;
        this.ticket = ticket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public boolean isOkeys() {
        return okeys;
    }

    public void setOkeys(boolean okeys) {
        this.okeys = okeys;
    }

    public List<Services> getServices() {
        return services;
    }

    public void setServices(List<Services> services) {
        this.services = services;
    }

    public Customer_preferences getCustomer_preferences() {
        return customer_preferences;
    }

    public void setCustomer_preferences(Customer_preferences customer_preferences) {
        this.customer_preferences = customer_preferences;
    }

    public Collection<Plain_training> getTenants() {
        return tenants;
    }

    public void setTenants(Collection<Plain_training> tenants) {
        this.tenants = tenants;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
