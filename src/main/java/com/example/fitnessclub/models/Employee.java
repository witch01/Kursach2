package com.example.fitnessclub.models;

import jakarta.persistence.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
public class Employee {
    public  Employee(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min=2, max = 50, message = "Фамилия не можетт быть короче двух и длиннее 50 символов.")
    private String surname;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min=2, max = 50, message = "Имя не можетт быть короче двух и длиннее 50 символов.")
    private String name;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min=2, max = 50, message = "Отчество не можетт быть короче двух и длиннее 50 символов.")
    private String middle_name;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min=10, max = 200, message = "Почта не может быть короче десяти и длиннее двухсот символов.")
    @Email(message = "Указан несуществующий адрес электронной почты")
    private String email;

    @Digits(message="Номер должен содеражть 10 символов.", fraction = 0, integer = 10)
    private String num_phone;

    @Size(min=10, max=10, message = "Серия и номер паспорт не может быть короче или длиннее десяти символов.")
    private String passport;

    @Size(min=8, max=8, message = "СНИЛС не может быть короче или длиннее восьми символов.")
    private String snils;

    @Size(min=12, max=12, message = "ИНН не может быть короче или длиннее двенадцати символов.")
    private String inn;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Post posts;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    private Collection<Plain_training> tenants;


    //private String login;
    //private String passwd;


    public Employee(String surname, String name, String middle_name, String email, String num_phone, String passport, String snils, String inn, Post posts, Collection<Plain_training> tenants) {
        this.surname = surname;
        this.name = name;
        this.middle_name = middle_name;
        this.email = email;
        this.num_phone = num_phone;
        this.passport = passport;
        this.snils = snils;
        this.inn = inn;
        this.posts = posts;
        this.tenants = tenants;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNum_phone() {
        return num_phone;
    }

    public void setNum_phone(String num_phone) {
        this.num_phone = num_phone;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getSnils() {
        return snils;
    }

    public void setSnils(String snils) {
        this.snils = snils;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public Post getPosts() {
        return posts;
    }

    public void setPosts(Post posts) {
        this.posts = posts;
    }

    public Collection<Plain_training> getTenants() {
        return tenants;
    }

    public void setTenants(Collection<Plain_training> tenants) {
        this.tenants = tenants;
    }
}
