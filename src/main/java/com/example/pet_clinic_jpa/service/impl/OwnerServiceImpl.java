package com.example.pet_clinic_jpa.service.impl;

import com.example.pet_clinic_jpa.domain.Owner;
import com.example.pet_clinic_jpa.exception.EntityNotExistException;
import com.example.pet_clinic_jpa.repo.OwnerJpaRepository;
import com.example.pet_clinic_jpa.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@RequiredArgsConstructor
@Component
public class OwnerServiceImpl implements OwnerService {

    private final OwnerJpaRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Owner getOwnerById(Long id) {
        return repository.getOwnerById(id).orElseThrow(() -> new EntityNotExistException(Owner.class, id));
    }

    @Override
    @Transactional(readOnly = true)
    public Owner getOwnerByIdWithRelatedPets(Long id) {
        return repository.getOwnerByIdWithRelatedPets(id).orElseThrow(() -> new EntityNotExistException(Owner.class, id));
    }

    @Override
    @Transactional
    public Owner createOwner(Owner owner) {
        return repository.save(owner);
    }

    @Override
    @Transactional
    public Owner updateOwner(Owner owner) {
        var persistedOwner = repository
                .getOwnerById(owner.getId())
                .orElseThrow(() -> new EntityNotExistException(Owner.class, owner.getId()));

        if (!Objects.equals(owner.getSurname(), persistedOwner.getSurname()) && Objects.nonNull(owner.getSurname())) {
            persistedOwner.setSurname(owner.getSurname());
        }

        if (!Objects.equals(owner.getName(), persistedOwner.getName()) && Objects.nonNull(owner.getName())) {
            persistedOwner.setSurname(owner.getName());
        }

        if (!Objects.equals(owner.getAddress(), persistedOwner.getAddress()) && Objects.nonNull(owner.getAddress())) {
            persistedOwner.setSurname(owner.getAddress());
        }

        if (!Objects.equals(owner.getMobilePhone(), persistedOwner.getMobilePhone()) && Objects.nonNull(owner.getMobilePhone())) {
            persistedOwner.setSurname(owner.getMobilePhone());
        }

        return persistedOwner;
    }
}
