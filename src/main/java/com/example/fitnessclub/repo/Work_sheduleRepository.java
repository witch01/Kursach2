package com.example.fitnessclub.repo;

import com.example.fitnessclub.models.Post;
import com.example.fitnessclub.models.Work_shedule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface Work_sheduleRepository extends CrudRepository<Work_shedule, Long> {

//    Work_shedule findByDay_work(String day_work);
//
//    List<Work_shedule> findByDay_workContains(String day_work);

}
