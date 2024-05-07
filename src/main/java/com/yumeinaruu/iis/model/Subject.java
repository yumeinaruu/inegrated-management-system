package com.yumeinaruu.iis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Entity(name = "subject")
@Data
@Component
public class Subject implements Serializable {
    @Id
    @SequenceGenerator(name = "subjectSeqGen", sequenceName = "subject_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "subjectSeqGen")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "department_id", nullable = false)
    private Long departmentId;
}
