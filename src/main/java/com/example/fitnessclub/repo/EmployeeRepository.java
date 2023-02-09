package com.example.fitnessclub.repo;


import com.example.fitnessclub.models.Employee;
import com.example.fitnessclub.models.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


    public interface EmployeeRepository extends CrudRepository<Employee, Long> {

        List<Employee> findBySurnameContains(String surname);
        Employee findBySurname(String surname);


    }


