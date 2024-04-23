package com.yumeinaruu.iis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import org.springframework.stereotype.Component;

@Entity(name = "marks")
@Data
@Component
public class Marks {
    @Id
    @SequenceGenerator(name = "marksIdSeqGen", sequenceName = "marks_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "marksIdSeqGen")
    private Long id;

    @Column(name = "mark", nullable = false)
    private Integer mark;

    @Column(name = "subject_id", nullable = false)
    private Long subjectId;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}
