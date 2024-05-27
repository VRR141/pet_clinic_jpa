package com.example.pet_clinic_jpa.service.impl;

import com.example.pet_clinic_jpa.config.RedisCacheConfig;
import com.example.pet_clinic_jpa.domain.Pet;
import com.example.pet_clinic_jpa.exception.EntityNotExistException;
import com.example.pet_clinic_jpa.repo.OwnerJpaRepository;
import com.example.pet_clinic_jpa.repo.PetJpaRepository;
import com.example.pet_clinic_jpa.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetJpaRepository repository;

    private final OwnerJpaRepository ownerJpaRepository;

    private final RedisTemplate<String, Pet> redisTemplate;

    private static final String PETS_KEY = RedisCacheConfig.PET_CACHE + "::";

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = RedisCacheConfig.PET_CACHE, key = "#id")
    public Pet getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotExistException(Pet.class, id));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Pet> getPetsByIdentifiers(Collection<Long> ids) {
        var keys = ids.stream().map(el -> PETS_KEY + el).collect(Collectors.toSet());

        var cachedPets = redisTemplate.opsForValue().multiGet(keys).stream().filter(Objects::nonNull).collect(Collectors.toList());

        var cachedIdentifiers = cachedPets.stream().map(Pet::getId).toList();

        var nonCachedIdentifiers = new HashSet<Long>();

        for (var el : ids) {
            if (!cachedIdentifiers.contains(el)) {
                nonCachedIdentifiers.add(el);
            }
        }

        if (!nonCachedIdentifiers.isEmpty()) {
            var persistedPets = repository.findByIdentifiersIn(nonCachedIdentifiers);
            if (persistedPets.isEmpty()){
                return cachedPets;
            }

            var toCache = persistedPets.stream()
                    .collect(Collectors.toMap(
                            k -> PETS_KEY + k.getId(),
                            Function.identity()));

            redisTemplate.opsForValue().multiSet(toCache);
            cachedPets.addAll(persistedPets);
        }

        return cachedPets;
    }

    @Override
    @Transactional
    @CachePut(value = RedisCacheConfig.PET_CACHE, key = "#pet.id")
    public Pet createPet(Pet pet) {
        var owner = ownerJpaRepository.getReferenceById(pet.getOwner().getId());
        pet.setOwner(owner);
        return repository.save(pet);
    }

    @Override
    @Transactional
    @CachePut(value = RedisCacheConfig.PET_CACHE, key = "#pet.id")
    public Pet updatePet(Pet pet) {
        var persisted = repository.findById(pet.getId()).orElseThrow(() -> new EntityNotExistException(Pet.class, pet.getId()));

        if (!Objects.equals(persisted.getName(), pet.getName()) && Objects.nonNull(pet.getName())) {
            persisted.setName(pet.getName());
        }

        if (!Objects.equals(persisted.getBirthDate(), pet.getBirthDate()) && Objects.nonNull(pet.getBirthDate())) {
            persisted.setBirthDate(pet.getBirthDate());
        }

        return persisted;
    }
}
