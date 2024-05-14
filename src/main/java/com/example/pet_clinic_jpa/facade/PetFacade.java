package com.example.pet_clinic_jpa.facade;

import com.example.pet_clinic_jpa.dto.PetDto;
import com.example.pet_clinic_jpa.dto.request.CreatePetDto;
import com.example.pet_clinic_jpa.dto.request.UpdatePetDto;

public interface PetFacade {

    PetDto getPet(Long id);

    Long createPet(CreatePetDto dto);

    PetDto updatePet(UpdatePetDto dto);
}
