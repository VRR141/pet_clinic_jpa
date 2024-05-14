package com.example.pet_clinic_jpa.facade;

import com.example.pet_clinic_jpa.dto.VisitDto;
import com.example.pet_clinic_jpa.dto.request.CreateVisitDto;

import java.util.Collection;

public interface VisitFacade {

    Collection<VisitDto> getByPetIdentifier(Long id);

    Long createVisit(CreateVisitDto dto);
}
