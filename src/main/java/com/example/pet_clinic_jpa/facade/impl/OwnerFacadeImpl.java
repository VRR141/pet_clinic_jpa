package com.example.pet_clinic_jpa.facade.impl;

import com.example.pet_clinic_jpa.domain.Owner;
import com.example.pet_clinic_jpa.dto.OwnerDto;
import com.example.pet_clinic_jpa.dto.request.CreateOwnerDto;
import com.example.pet_clinic_jpa.dto.request.UpdateOwnerDto;
import com.example.pet_clinic_jpa.facade.OwnerFacade;
import com.example.pet_clinic_jpa.mapper.OwnerMapper;
import com.example.pet_clinic_jpa.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OwnerFacadeImpl implements OwnerFacade {

    private final OwnerService ownerService;

    private final OwnerMapper mapper;

    @Override
    public OwnerDto getOwner(Long id, boolean fetchPets) {
        final Owner owner;
        final OwnerDto ownerDto;
        if (fetchPets) {
            owner = ownerService.getOwnerByIdWithRelatedPets(id);
            ownerDto = mapper.map(owner);
        } else {
            owner = ownerService.getOwnerById(id);
            ownerDto = mapper.mapIgnorePets(owner);
        }
        return ownerDto;
    }

    @Override
    public Long createOwner(CreateOwnerDto dto) {
        var owner = mapper.map(dto);
        owner = ownerService.createOwner(owner);
        return owner.getId();
    }

    @Override
    public OwnerDto updateOwner(UpdateOwnerDto dto) {
        var owner = mapper.map(dto);
        owner = ownerService.updateOwner(owner);
        return mapper.mapIgnorePets(owner);
    }
}
