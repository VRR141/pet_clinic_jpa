package com.example.pet_clinic_jpa.service.impl;

import com.example.pet_clinic_jpa.config.RedisCacheConfig;
import com.example.pet_clinic_jpa.domain.Visit;
import com.example.pet_clinic_jpa.repo.PetJpaRepository;
import com.example.pet_clinic_jpa.repo.VisitJpaRepository;
import com.example.pet_clinic_jpa.service.VisitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class VisitServiceImpl implements VisitService {

    private final VisitJpaRepository repository;
    private final PetJpaRepository petJpaRepository;
    private final RedisTemplate<String, List<Visit>> redisTemplate;
    private static final String VISIT_KEY = RedisCacheConfig.VISIT_CACHE + "::";

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = RedisCacheConfig.VISIT_CACHE, key = "#id")
    public Collection<Visit> getVisitsByPetIdentifier(Long id) {
        return repository.getVisitByPetIdentifier(id);
    }


    @Override
    @Transactional
    public Visit createVisit(Visit visit) {
        var stopWatch = new StopWatch();
        var pet = petJpaRepository.getReferenceById(visit.getPet().getId());
        visit.setPet(pet);
        var saved = repository.save(visit);
        var visits = redisTemplate.opsForValue().get(VISIT_KEY + pet.getId());
        if (Objects.isNull(visits) || visits.isEmpty()) {
            visits = new ArrayList<>();
        }
        visits.add(saved);
        redisTemplate.opsForValue().set(VISIT_KEY + pet.getId(), visits, RedisCacheConfig.VISIT_CACHE_DURATION);
        return saved;
    }
}
