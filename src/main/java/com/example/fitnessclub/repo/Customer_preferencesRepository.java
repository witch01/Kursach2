package com.example.fitnessclub.repo;

import com.example.fitnessclub.models.Client;
import com.example.fitnessclub.models.Customer_preferences;
import org.springframework.data.repository.CrudRepository;

import java.util.List;



public interface Customer_preferencesRepository extends CrudRepository<Customer_preferences, Long> {

    List<Customer_preferences> findByPreferencesContains(String preferences);
    Client findByPreferences(String preferences);


}

