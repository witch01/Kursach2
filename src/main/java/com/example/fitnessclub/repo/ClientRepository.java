package com.example.fitnessclub.repo;

import com.example.fitnessclub.models.Client;
import com.example.fitnessclub.models.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ClientRepository extends CrudRepository<Client, Long> {

    List<Client> findByPhoneContains(String phone);
    Client findByPhone(String phone);


}
