package com.yumeinaruu.iis.repository;

import com.yumeinaruu.iis.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByGroupId(Long groupId);
    List<Schedule> findAllBySubjectId(Long subjectId);
}
