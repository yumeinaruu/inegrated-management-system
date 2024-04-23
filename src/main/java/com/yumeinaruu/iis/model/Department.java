package com.yumeinaruu.iis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import org.springframework.stereotype.Component;

@Entity(name = "department")
@Data
@Component
public class Department {
    @Id
    @SequenceGenerator(name = "depIdSeqGen", sequenceName = "department_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "depIdSeqGen")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
