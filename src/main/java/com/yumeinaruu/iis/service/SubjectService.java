package com.yumeinaruu.iis.service;

import com.yumeinaruu.iis.model.Subject;
import com.yumeinaruu.iis.model.dto.subject.SubjectCreateDto;
import com.yumeinaruu.iis.model.dto.subject.SubjectDepartmentUpdateDto;
import com.yumeinaruu.iis.model.dto.subject.SubjectNameUpdateDto;
import com.yumeinaruu.iis.model.dto.subject.SubjectUpdateDto;
import com.yumeinaruu.iis.repository.DepartmentRepository;
import com.yumeinaruu.iis.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository, DepartmentRepository departmentRepository) {
        this.subjectRepository = subjectRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Optional<Subject> getSubjectById(Long id) {
        return subjectRepository.findById(id);
    }

    public Optional<Subject> getSubjectByName(String name) {
        return subjectRepository.findByName(name);
    }

    public List<Subject> getSubjectsSortedByName() {
        return subjectRepository.findAll(Sort.by("name"));
    }

    public Boolean createSubject(SubjectCreateDto subjectCreateDto) {
        Subject subject = new Subject();
        subject.setName(subjectCreateDto.getName());
        if (departmentRepository.findByName(subjectCreateDto.getDepartment()).isPresent()) {
            subject.setDepartmentId(departmentRepository.findByName(subjectCreateDto.getDepartment()).get().getId());
        }
        Subject savedSubject = subjectRepository.save(subject);
        return getSubjectById(savedSubject.getId()).isPresent();
    }

    public Boolean updateSubject(SubjectUpdateDto subjectUpdateDto) {
        Optional<Subject> optionalSubject = subjectRepository.findById(subjectUpdateDto.getId());
        if(optionalSubject.isPresent()) {
            Subject subject = optionalSubject.get();
            subject.setName(subjectUpdateDto.getName());
            if(departmentRepository.findByName(subjectUpdateDto.getDepartment()).isPresent()) {
                subject.setDepartmentId(departmentRepository.findByName(subjectUpdateDto.getDepartment()).get().getId());
            }
            Subject savedSubject = subjectRepository.save(subject);
            return savedSubject.equals(subject);
        }
        return false;
    }

    public Boolean updateSubjectName(SubjectNameUpdateDto subjectNameUpdateDto) {
        Optional<Subject> optionalSubject = subjectRepository.findById(subjectNameUpdateDto.getId());
        if(optionalSubject.isPresent()) {
            Subject subject = optionalSubject.get();
            subject.setName(subjectNameUpdateDto.getName());
            Subject savedSubject = subjectRepository.save(subject);
            return savedSubject.equals(subject);
        }
        return false;
    }

    public Boolean updateSubjectDepartment(SubjectDepartmentUpdateDto subjectDepartmentUpdateDto) {
        Optional<Subject> optionalSubject = subjectRepository.findById(subjectDepartmentUpdateDto.getId());
        if(optionalSubject.isPresent()) {
            Subject subject = optionalSubject.get();
            if(departmentRepository.findByName(subjectDepartmentUpdateDto.getDepartment()).isPresent()) {
                subject.setDepartmentId(departmentRepository.findByName(subjectDepartmentUpdateDto.getDepartment()).get().getId());
            }
            Subject savedSubject = subjectRepository.save(subject);
            return savedSubject.equals(subject);
        }
        return false;
    }

    public Boolean deleteSubject(Long id) {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        if(optionalSubject.isEmpty()) {
            return false;
        }
        subjectRepository.delete(optionalSubject.get());
        return true;
    }
}
