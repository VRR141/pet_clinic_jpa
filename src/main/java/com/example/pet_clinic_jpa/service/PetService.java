package com.example.pet_clinic_jpa.service;

import com.example.pet_clinic_jpa.domain.Pet;

public interface PetService {

    Pet getById(Long id);

    Pet createPet(Pet pet);

    Pet updatePet(Pet pet);
}
