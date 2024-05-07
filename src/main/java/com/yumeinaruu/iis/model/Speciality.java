package com.yumeinaruu.iis.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;

@Entity(name = "speciality")
@Data
@Component
public class Speciality implements Serializable {
    @Id
    @SequenceGenerator(name = "specSeqGen", sequenceName = "speciality_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "specSeqGen")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @JsonBackReference
    @JoinColumn(name = "faculty_id", nullable = false)
    @ManyToOne
    private Faculty facultyId;

    @OneToMany(mappedBy = "specialityId", fetch = FetchType.EAGER)
    private Collection<Group> groups;
}

