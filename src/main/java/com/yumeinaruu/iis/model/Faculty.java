package com.yumeinaruu.iis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Entity(name = "faculty")
@Component
@Data
@ToString(exclude = "groups")
@EqualsAndHashCode(exclude = "groups")
public class Faculty {
    @Id
    @SequenceGenerator(name = "facIdSeqGen", sequenceName = "faculty_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "facIdSeqGen")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "facultyId", fetch = FetchType.EAGER)
    private Collection<Group> groups;

    @OneToMany(mappedBy = "facultyId", fetch = FetchType.EAGER)
    private Collection<Speciality> specialities;
}
