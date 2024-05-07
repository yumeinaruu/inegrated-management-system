package com.yumeinaruu.iis.service;

import com.yumeinaruu.iis.model.Faculty;
import com.yumeinaruu.iis.model.dto.faculty.FacultyCreateDto;
import com.yumeinaruu.iis.model.dto.faculty.FacultyUpdateDto;
import com.yumeinaruu.iis.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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

    @Cacheable(value = "FacultyService::getFacultyById", key = "#id")
    public Optional<Faculty> getFacultyById(Long id) {
        return facultyRepository.findById(id);
    }

    @Cacheable(value = "FacultyService::getFacultyByName", key = "#name")
    public Optional<Faculty> getFacultyByName(String name) {
        return facultyRepository.findByName(name);
    }

    @Caching(cacheable = {
            @Cacheable(value = "FacultyService::getFacultyByName", key = "#facultyCreateDto.name")

    })
    public Boolean createFaculty(FacultyCreateDto facultyCreateDto) {
        Faculty faculty = new Faculty();
        faculty.setName(facultyCreateDto.getName());
        Faculty createFaculty = facultyRepository.save(faculty);
        return getFacultyById(createFaculty.getId()).isPresent();
    }

    @Caching(put = {
            @CachePut(value = "FacultyService::getFacultyById", key = "#facultyUpdateDto.id"),
            @CachePut(value = "FacultyService::getFacultyByName", key = "#facultyUpdateDto.name")

    })
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

    @CacheEvict(value = "FacultyService::getFacultyById", key = "#id")
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
