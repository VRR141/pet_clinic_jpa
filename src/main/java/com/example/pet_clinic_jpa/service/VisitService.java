package com.example.pet_clinic_jpa.service;

import com.example.pet_clinic_jpa.domain.Visit;

import java.util.Collection;

public interface VisitService {

    Collection<Visit> getVisitsByPetIdentifier(Long id);

    Visit createVisit(Visit visit);
}
