package com.example.pet_clinic_jpa.service;

import com.example.pet_clinic_jpa.domain.Owner;

public interface OwnerService {

    Owner getOwnerById(Long id);

    Owner getOwnerByIdWithRelatedPets(Long id);

    Owner createOwner(Owner owner);

    Owner updateOwner(Owner owner);
}
