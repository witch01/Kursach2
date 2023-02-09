package com.example.fitnessclub.repo;

import com.example.fitnessclub.models.Post;
import com.example.fitnessclub.models.Recomended_services;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface Recomended_servicesRepository extends CrudRepository<Recomended_services, Long> {

//    Recomended_services findByNames(String names);
//
//    List<Post> findByNamesContains(String names);

}
