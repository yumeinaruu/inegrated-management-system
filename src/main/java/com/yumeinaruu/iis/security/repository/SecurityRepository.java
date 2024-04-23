package com.yumeinaruu.iis.security.repository;

import com.yumeinaruu.iis.security.model.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityRepository extends JpaRepository<Security, Long> {
}
