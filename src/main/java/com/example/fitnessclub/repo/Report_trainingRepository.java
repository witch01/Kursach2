package com.example.fitnessclub.repo;

import com.example.fitnessclub.models.Report_training;
import com.example.fitnessclub.models.Services;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface Report_trainingRepository extends CrudRepository<Report_training, Long> {

//    Report_training findByCent(double cent);
//
//    List<Services> findByCentContains(double cent);

}
