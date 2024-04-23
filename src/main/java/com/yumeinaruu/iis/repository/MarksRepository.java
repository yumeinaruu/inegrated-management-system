package com.yumeinaruu.iis.repository;

import com.yumeinaruu.iis.model.Marks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarksRepository extends JpaRepository<Marks, Long> {
}
