package com.example.fitnessclub.repo;

import com.example.fitnessclub.models.City_services;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface City_servicesRepository extends CrudRepository<City_services, Long> {

    City_services findByName(String name);

    List<City_services> findByNameContains(String name);


}

