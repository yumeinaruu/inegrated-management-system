package com.yumeinaruu.iis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Entity(name = "schedule")
@Component
@Data
public class Schedule {
    @Id
    @SequenceGenerator(name = "scheduleIdSeqGen", sequenceName = "schedule_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "scheduleIdSeqGen")
    private Long id;

    @Column(name = "subject_id", nullable = false)
    private Long subjectId;

    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "beginning", nullable = false)
    private Timestamp beginning;

    @Column(name = "ending", nullable = false)
    private Timestamp ending;
}
