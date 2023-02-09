package com.example.fitnessclub.repo;

import com.example.fitnessclub.models.City_services;
import com.example.fitnessclub.models.Post;
import com.example.fitnessclub.models.Services;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ServicesRepository extends CrudRepository<Services, Long> {

    Services findByCent(double cent);

    List<Services> findByCentContains(double cent);

}
