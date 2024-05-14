package com.example.pet_clinic_jpa.facade;

import com.example.pet_clinic_jpa.domain.Pet;
import com.example.pet_clinic_jpa.dto.PetDto;
import com.example.pet_clinic_jpa.dto.request.CreatePetDto;
import com.example.pet_clinic_jpa.dto.request.UpdatePetDto;

import java.util.Collection;

public interface PetFacade {

    PetDto getPet(Long id);

    Long createPet(CreatePetDto dto);

    PetDto updatePet(UpdatePetDto dto);

    Collection<PetDto> getPets(Collection<Long> ids);
}
