package com.example.pet_clinic_jpa.mapper;

import com.example.pet_clinic_jpa.domain.Pet;
import com.example.pet_clinic_jpa.dto.PetDto;
import com.example.pet_clinic_jpa.dto.request.CreatePetDto;
import com.example.pet_clinic_jpa.dto.request.UpdatePetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = VisitMapper.class)
public interface PetMapper {

    PetDto map(Pet entity);

    @Mappings({
            @Mapping(target = "visits", ignore = true)
    })
    PetDto mapIgnoreVisits(Pet entity);

    @Mappings({
            @Mapping(target = "owner.id", source = "ownerId"),
    })
    Pet map(CreatePetDto dto);

    Pet map(UpdatePetDto dto);
}
