package com.yumeinaruu.iis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import org.springframework.stereotype.Component;

@Entity(name = "faculty")
@Component
@Data
public class Faculty {
    @Id
    @SequenceGenerator(name = "facIdSeqGen", sequenceName = "faculty_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "facIdSeqGen")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
