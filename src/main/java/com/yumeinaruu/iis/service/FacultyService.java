package com.yumeinaruu.iis.service;

import com.yumeinaruu.iis.model.Faculty;
import com.yumeinaruu.iis.model.dto.faculty.FacultyCreateDto;
import com.yumeinaruu.iis.model.dto.faculty.FacultyUpdateDto;
import com.yumeinaruu.iis.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public Optional<Faculty> getFacultyById(Long id) {
        return facultyRepository.findById(id);
    }

    public Boolean createFaculty(FacultyCreateDto facultyCreateDto) {
        Faculty faculty = new Faculty();
        faculty.setName(facultyCreateDto.getName());
        Faculty createFaculty = facultyRepository.save(faculty);
        return getFacultyById(createFaculty.getId()).isPresent();
    }

    public Boolean updateFaculty(FacultyUpdateDto facultyUpdateDto) {
        Optional<Faculty> optionalFaculty = facultyRepository.findById(facultyUpdateDto.getId());
        if (optionalFaculty.isPresent()) {
            Faculty updatedFaculty = optionalFaculty.get();
            updatedFaculty.setName(facultyUpdateDto.getName());
            Faculty updateFaculty = facultyRepository.saveAndFlush(updatedFaculty);
            return updatedFaculty.equals(updateFaculty);
        }
        return false;
    }

    public Boolean deleteFaculty(Long id) {
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isEmpty()) {
            return false;
        }
        facultyRepository.delete(optionalFaculty.get());
        return true;
    }

    public List<Faculty> getFacultiesSortedByName() {
        return facultyRepository.findAll(Sort.by("name"));
    }
}
