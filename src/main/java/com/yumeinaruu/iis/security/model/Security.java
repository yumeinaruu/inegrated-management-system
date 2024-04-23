package com.yumeinaruu.iis.security.model;

import com.yumeinaruu.iis.security.model.dto.Roles;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import org.springframework.stereotype.Component;

@Entity(name = "security")
@Data
@Component
public class Security {
    @Id
    @SequenceGenerator(name = "securitySeqGen", sequenceName = "security_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "securitySeqGen")
    private Long id;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated
    @Column(name = "role", nullable = false)
    private Roles role;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}
