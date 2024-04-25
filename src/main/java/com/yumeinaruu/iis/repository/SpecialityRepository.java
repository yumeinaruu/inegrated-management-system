package com.yumeinaruu.iis.repository;

import com.yumeinaruu.iis.model.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long> {
    Optional<Speciality> findByName(String name);
}
