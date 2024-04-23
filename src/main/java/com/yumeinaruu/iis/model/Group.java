package com.yumeinaruu.iis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import org.springframework.stereotype.Component;

@Entity(name = "group")
@Data
@Component
public class Group {
    @Id
    @SequenceGenerator(name = "groupIdSeqGen", sequenceName = "group_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "groupIdSeqGen")
    private Long id;

    @Column(name = "group_name", nullable = false, unique = true)
    private String groupName;

    @Column(name = "faculty_id", nullable = false)
    private Long facultyId;

    @Column(name = "speciality_id", nullable = false)
    private Long specialityId;
}
