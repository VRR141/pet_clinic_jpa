package com.example.pet_clinic_jpa.repo;

import com.example.pet_clinic_jpa.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OwnerJpaRepository extends JpaRepository<Owner, Long> {

    @Query("""
            FROM Owner owner
            WHERE owner.id = :id
            """)
    Optional<Owner> getOwnerById(Long id);

    @Query("""
            FROM Owner owner
            LEFT JOIN FETCH owner.pets
            WHERE owner.id = :id
            """)
    Optional<Owner> getOwnerByIdWithRelatedPets(Long id);
}
