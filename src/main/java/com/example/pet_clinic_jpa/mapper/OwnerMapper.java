package com.example.pet_clinic_jpa.mapper;

import com.example.pet_clinic_jpa.domain.Owner;
import com.example.pet_clinic_jpa.domain.Pet;
import com.example.pet_clinic_jpa.dto.OwnerDto;
import com.example.pet_clinic_jpa.dto.PetDto;
import com.example.pet_clinic_jpa.dto.request.CreateOwnerDto;
import com.example.pet_clinic_jpa.dto.request.UpdateOwnerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface OwnerMapper {

    OwnerDto map(Owner entity);

    @Mappings({
            @Mapping(target = "pets", ignore = true)
    })
    OwnerDto mapIgnorePets(Owner entity);

    Owner map(CreateOwnerDto dto);

    Owner map(UpdateOwnerDto dto);

    @Mappings({
            @Mapping(target = "visits", ignore = true)
    })
    PetDto map(Pet pet);
}
