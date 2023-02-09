package com.example.fitnessclub.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.sql.Date;
import java.sql.Time;

@Entity
public class Report_training {
    public Report_training(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @PastOrPresent(message = "Указана неверная дата")
    private Date date_report;
    private Time time_report;

    @NotEmpty(message = "Поле не может быть пустым")
    private String report;
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Employee employee;

    public Report_training(Date date_report, Time time_report, String report, Employee employee) {
        this.date_report = date_report;
        this.time_report = time_report;
        this.report = report;
        this.employee = employee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate_report() {
        return date_report;
    }

    public void setDate_report(Date date_report) {
        this.date_report = date_report;
    }

    public Time getTime_report() {
        return time_report;
    }

    public void setTime_report(Time time_report) {
        this.time_report = time_report;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
