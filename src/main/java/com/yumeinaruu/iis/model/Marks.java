package com.yumeinaruu.iis.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Entity(name = "marks")
@Data
@Component
public class Marks implements Serializable {
    @Id
    @SequenceGenerator(name = "marksIdSeqGen", sequenceName = "marks_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "marksIdSeqGen")
    private Long id;

    @Column(name = "mark", nullable = false)
    private Integer mark;

    @Column(name = "subject_id", nullable = false)
    private Long subjectId;

    @JsonBackReference
    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    private Users userId;
}
