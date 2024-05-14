package com.example.pet_clinic_jpa.repo;

import com.example.pet_clinic_jpa.domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VisitJpaRepository extends JpaRepository<Visit, Long> {

    @Query("""
                FROM Visit visit
                WHERE visit.pet.id = :id
            """)
    List<Visit> getVisitByPetIdentifier(Long id);
}
