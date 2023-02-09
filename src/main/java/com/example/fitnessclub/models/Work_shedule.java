package com.example.fitnessclub.models;

import jakarta.persistence.*;

import javax.validation.constraints.NotEmpty;
import java.sql.Time;

@Entity
public class Work_shedule {
    public Work_shedule(){}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Time time_work;

    @NotEmpty(message = "Поле не может быть пустым")
    private String day_work;
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Employee employee;
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Services services;

    public Work_shedule(Time time_work, String day_work, Employee employee, Services services) {
        this.time_work = time_work;
        this.day_work = day_work;
        this.employee = employee;
        this.services = services;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Time getTime_work() {
        return time_work;
    }

    public void setTime_work(Time time_work) {
        this.time_work = time_work;
    }

    public String getDay_work() {
        return day_work;
    }

    public void setDay_work(String day_work) {
        this.day_work = day_work;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Services getServices() {
        return services;
    }

    public void setServices(Services services) {
        this.services = services;
    }


}
