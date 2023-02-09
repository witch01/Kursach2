package com.example.fitnessclub.repo;

import com.example.fitnessclub.models.Plain_training;
import com.example.fitnessclub.models.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface Plain_trainingRepository extends CrudRepository<Plain_training, Long> {

    Plain_training findByPlain(String plain);

    List<Plain_training> findByPlainContains(String plain);

}

