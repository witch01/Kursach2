package com.example.fitnessclub.repo;

import com.example.fitnessclub.models.Ticket;
import com.example.fitnessclub.models.Visit;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VisitRepository extends CrudRepository<Visit, Long> {
    List<Visit> findAll();
}