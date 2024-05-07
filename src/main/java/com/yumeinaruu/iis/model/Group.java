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

import java.util.Collection;

@Entity(name = "groups")
@Data
@Component
public class Group {
    @Id
    @SequenceGenerator(name = "groupIdSeqGen", sequenceName = "group_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "groupIdSeqGen")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @JsonBackReference
    @JoinColumn(name = "faculty_id", nullable = false)
    @ManyToOne
    private Faculty facultyId;

    @JsonBackReference
    @JoinColumn(name = "speciality_id", nullable = false)
    @ManyToOne
    private Speciality specialityId;

    @OneToMany(mappedBy = "groupId", fetch = FetchType.EAGER)
    private Collection<Users> users;
}