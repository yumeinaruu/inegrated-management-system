package com.yumeinaruu.iis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import org.springframework.stereotype.Component;

@Entity(name = "speciality")
@Data
@Component
public class Speciality {
    @Id
    @SequenceGenerator(name = "specSeqGen", sequenceName = "speciality_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "specSeqGen")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "faculty_id", nullable = false)
    private Long facultyId;
}

