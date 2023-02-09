package com.example.fitnessclub.Services;

import com.example.fitnessclub.models.Visit;
import com.example.fitnessclub.repo.VisitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticService {
    private final VisitRepository visitRepository;

    public StatisticService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public void logVisit(Visit visit) {
        visitRepository.save(visit);
    }

    public List<Visit> getVisits() {
        return visitRepository.findAll();
    }
}