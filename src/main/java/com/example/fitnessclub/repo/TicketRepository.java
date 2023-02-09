package com.example.fitnessclub.repo;

import com.example.fitnessclub.models.Services;
import com.example.fitnessclub.models.Ticket;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface TicketRepository extends CrudRepository<Ticket, Long> {

    Ticket findByCent(double cent);

    List<Ticket> findByCentContains(double cent);

}
