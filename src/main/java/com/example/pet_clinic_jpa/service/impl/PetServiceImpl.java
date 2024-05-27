package com.example.pet_clinic_jpa.service.impl;

import com.example.pet_clinic_jpa.domain.Pet;
import com.example.pet_clinic_jpa.exception.EntityNotExistException;
import com.example.pet_clinic_jpa.repo.OwnerJpaRepository;
import com.example.pet_clinic_jpa.repo.PetJpaRepository;
import com.example.pet_clinic_jpa.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetJpaRepository repository;

    private final OwnerJpaRepository ownerJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public Pet getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotExistException(Pet.class, id));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Pet> getPetsByIdentifiers(Collection<Long> ids) {
        return repository.findByIdentifiersIn(ids);
    }

    @Override
    @Transactional
    public Pet createPet(Pet pet) {
        var owner = ownerJpaRepository.getReferenceById(pet.getOwner().getId());
        pet.setOwner(owner);
        return repository.save(pet);
    }

    @Override
    @Transactional
    public Pet updatePet(Pet pet) {
        var persisted = repository.findById(pet.getId()).orElseThrow(() -> new EntityNotExistException(Pet.class, pet.getId()));

        if (!Objects.equals(persisted.getName(), pet.getName()) && Objects.nonNull(pet.getName())) {
            persisted.setName(pet.getName());
        }

        if (!Objects.equals(persisted.getBirthDate(), pet.getBirthDate())&& Objects.nonNull(pet.getBirthDate())) {
            persisted.setBirthDate(pet.getBirthDate());
        }

        return persisted;
    }
}
