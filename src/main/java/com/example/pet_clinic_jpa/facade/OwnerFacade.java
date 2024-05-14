package com.example.pet_clinic_jpa.facade;

import com.example.pet_clinic_jpa.dto.OwnerDto;
import com.example.pet_clinic_jpa.dto.request.CreateOwnerDto;
import com.example.pet_clinic_jpa.dto.request.UpdateOwnerDto;

public interface OwnerFacade {

    OwnerDto getOwner(Long id, boolean fetchPets);

    Long createOwner(CreateOwnerDto dto);

    OwnerDto updateOwner(UpdateOwnerDto dto);
}
