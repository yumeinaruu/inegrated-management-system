package com.yumeinaruu.iis.service;

import com.yumeinaruu.iis.model.Group;
import com.yumeinaruu.iis.model.Schedule;
import com.yumeinaruu.iis.model.Subject;
import com.yumeinaruu.iis.model.dto.schedule.ScheduleCreateDto;
import com.yumeinaruu.iis.model.dto.schedule.ScheduleUpdateDto;
import com.yumeinaruu.iis.model.dto.schedule.ScheduleUpdateGroupDto;
import com.yumeinaruu.iis.model.dto.schedule.ScheduleUpdateSubjectDto;
import com.yumeinaruu.iis.model.dto.schedule.ScheduleUpdateTimeDto;
import com.yumeinaruu.iis.repository.GroupRepository;
import com.yumeinaruu.iis.repository.ScheduleRepository;
import com.yumeinaruu.iis.repository.SubjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, GroupRepository groupRepository, SubjectRepository subjectRepository) {
        this.scheduleRepository = scheduleRepository;
        this.groupRepository = groupRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Optional<Schedule> getScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }

    public List<Schedule> getScheduleByGroupName(String name) {
        Optional<Group> group = groupRepository.findByName(name);
        if (group.isPresent()) {
            return scheduleRepository.findAllByGroupId(group.get().getId());
        }
        return null;
    }

    public List<Schedule> getScheduleBySubjectName(String name) {
        Optional<Subject> subject = subjectRepository.findByName(name);
        if (subject.isPresent()) {
            return scheduleRepository.findAllBySubjectId(subject.get().getId());
        }
        return null;
    }

    public Boolean createSchedule(ScheduleCreateDto scheduleCreateDto) {
        Schedule schedule = new Schedule();
        schedule.setBeginning(scheduleCreateDto.getBeginning());
        schedule.setEnding(scheduleCreateDto.getEnding());
        if (subjectRepository.findByName(scheduleCreateDto.getSubject()).isPresent()) {
            schedule.setSubjectId(subjectRepository.findByName(scheduleCreateDto.getSubject()).get().getId());
        }
        if (groupRepository.findByName(scheduleCreateDto.getGroup()).isPresent()) {
            schedule.setGroupId(groupRepository.findByName(scheduleCreateDto.getGroup()).get().getId());
        }
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return getScheduleById(savedSchedule.getId()).isPresent();
    }

    public Boolean updateSchedule(ScheduleUpdateDto scheduleUpdateDto) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleUpdateDto.getId());
        if (optionalSchedule.isPresent()) {
            Schedule schedule = optionalSchedule.get();
            schedule.setBeginning(scheduleUpdateDto.getBeginning());
            schedule.setEnding(scheduleUpdateDto.getEnding());
            if (subjectRepository.findByName(scheduleUpdateDto.getSubject()).isPresent()) {
                schedule.setSubjectId(subjectRepository.findByName(scheduleUpdateDto.getSubject()).get().getId());
            }
            if (groupRepository.findByName(scheduleUpdateDto.getGroup()).isPresent()) {
                schedule.setGroupId(groupRepository.findByName(scheduleUpdateDto.getGroup()).get().getId());
            }
            Schedule savedSchedule = scheduleRepository.saveAndFlush(schedule);
            return savedSchedule.equals(schedule);
        }
        return false;
    }

    public Boolean scheduleUpdateTime(ScheduleUpdateTimeDto scheduleUpdateTimeDto) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleUpdateTimeDto.getId());
        if (optionalSchedule.isPresent()) {
            Schedule schedule = optionalSchedule.get();
            schedule.setBeginning(scheduleUpdateTimeDto.getBeginning());
            schedule.setEnding(scheduleUpdateTimeDto.getEnding());
            Schedule savedSchedule = scheduleRepository.saveAndFlush(schedule);
            return savedSchedule.equals(schedule);
        }
        return false;
    }

    public Boolean scheduleUpdateSubject(ScheduleUpdateSubjectDto scheduleUpdateSubjectDto) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleUpdateSubjectDto.getId());
        if (optionalSchedule.isPresent()) {
            Schedule schedule = optionalSchedule.get();
            if (subjectRepository.findByName(scheduleUpdateSubjectDto.getSubject()).isPresent()) {
                schedule.setSubjectId(subjectRepository.findByName(scheduleUpdateSubjectDto.getSubject()).get().getId());
            }
            Schedule savedSchedule = scheduleRepository.saveAndFlush(schedule);
            return savedSchedule.equals(schedule);
        }
        return false;
    }

    public Boolean scheduleUpdateGroup(ScheduleUpdateGroupDto scheduleUpdateGroupDto) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleUpdateGroupDto.getId());
        if (optionalSchedule.isPresent()) {
            Schedule schedule = optionalSchedule.get();
            if (groupRepository.findByName(scheduleUpdateGroupDto.getGroup()).isPresent()) {
                schedule.setGroupId(groupRepository.findByName(scheduleUpdateGroupDto.getGroup()).get().getId());
            }
            Schedule savedSchedule = scheduleRepository.saveAndFlush(schedule);
            return savedSchedule.equals(schedule);
        }
        return false;
    }

    public Boolean deleteScheduleById(Long id) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);
        if (optionalSchedule.isEmpty()) {
            return false;
        }
        scheduleRepository.delete(optionalSchedule.get());
        return true;
    }

    @Scheduled(fixedDelay = 20000)
    public void deleteExpiredSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        for (Schedule schedule : schedules) {
            if (schedule.getEnding().before(Timestamp.valueOf(LocalDateTime.now()))) {
                scheduleRepository.delete(schedule);
                log.info(schedule + " has been deleted in " + LocalDateTime.now());
            }
        }
    }
}
