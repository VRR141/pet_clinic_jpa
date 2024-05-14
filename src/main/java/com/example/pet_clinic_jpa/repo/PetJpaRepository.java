package com.example.pet_clinic_jpa.repo;

import com.example.pet_clinic_jpa.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PetJpaRepository extends JpaRepository<Pet, Long> {

    @Query("""
            FROM Pet pet
            LEFT JOIN FETCH pet.visits
            WHERE pet.id = :id
            """)
    Optional<Pet> findByIdWithFetchedVisits(Long id);

    @Query("""
            FROM Pet pet
            WHERE pet.id = :id
            """)
    Optional<Pet> findById(Long id);
}