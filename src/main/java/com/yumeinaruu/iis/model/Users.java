package com.yumeinaruu.iis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Entity(name = "users")
@Component
@Data
public class Users {
    @Id
    @SequenceGenerator(name = "usersIdSeqGen", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "usersIdSeqGen")
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp created;

    @Column(name = "changed")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp changed;

    @Column(name = "group_id")
    private Long groupId;
}
