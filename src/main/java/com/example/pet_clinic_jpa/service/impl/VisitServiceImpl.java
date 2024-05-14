package com.example.pet_clinic_jpa.service.impl;

import com.example.pet_clinic_jpa.domain.Visit;
import com.example.pet_clinic_jpa.repo.PetJpaRepository;
import com.example.pet_clinic_jpa.repo.VisitJpaRepository;
import com.example.pet_clinic_jpa.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitJpaRepository repository;

    private final PetJpaRepository petJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public Collection<Visit> getVisitsByPetIdentifier(Long id) {
        return repository.getVisitByPetIdentifier(id);
    }

    @Override
    @Transactional
    public Visit createVisit(Visit visit) {
        var pet = petJpaRepository.getReferenceById(visit.getPet().getId());
        visit.setPet(pet);
        return repository.save(visit);
    }
}
