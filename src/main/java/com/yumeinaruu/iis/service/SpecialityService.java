package com.yumeinaruu.iis.service;

import com.yumeinaruu.iis.model.Speciality;
import com.yumeinaruu.iis.model.dto.speciality.SpecialityCreateDto;
import com.yumeinaruu.iis.model.dto.speciality.SpecialityUpdateDto;
import com.yumeinaruu.iis.model.dto.speciality.SpecialityUpdateFacultyDto;
import com.yumeinaruu.iis.model.dto.speciality.SpecialityUpdateNameDto;
import com.yumeinaruu.iis.repository.FacultyRepository;
import com.yumeinaruu.iis.repository.SpecialityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecialityService {
    private final SpecialityRepository specialityRepository;
    private final FacultyRepository facultyRepository;

    @Autowired
    public SpecialityService(SpecialityRepository specialityRepository, FacultyRepository facultyRepository) {
        this.specialityRepository = specialityRepository;
        this.facultyRepository = facultyRepository;
    }

    public List<Speciality> getAllSpecialities() {
        return specialityRepository.findAll();
    }

    public Optional<Speciality> getSpecialityById(Long id) {
        return specialityRepository.findById(id);
    }

    public List<Speciality> getSpecialitiesSortedByName() {
        return specialityRepository.findAll(Sort.by("name"));
    }

    public Boolean createSpeciality(SpecialityCreateDto specialityCreateDto) {
        Speciality speciality = new Speciality();
        speciality.setName(specialityCreateDto.getName());
        if (facultyRepository.findByName(specialityCreateDto.getFaculty()).isPresent()) {
            speciality.setFacultyId(facultyRepository.findByName(specialityCreateDto.getFaculty()).get().getId());
        }
        Speciality savedSpeciality = specialityRepository.save(speciality);
        return getSpecialityById(savedSpeciality.getId()).isPresent();
    }

    public Boolean updateSpeciality(SpecialityUpdateDto specialityUpdateDto) {
        Optional<Speciality> optionalSpeciality = specialityRepository.findById(specialityUpdateDto.getId());
        if (optionalSpeciality.isPresent()) {
            Speciality speciality = optionalSpeciality.get();
            speciality.setName(specialityUpdateDto.getName());
            if (facultyRepository.findByName(specialityUpdateDto.getFaculty()).isPresent()) {
                speciality.setFacultyId(facultyRepository.findByName(specialityUpdateDto.getFaculty()).get().getId());
            }
            Speciality savedSpeciality = specialityRepository.save(speciality);
            return savedSpeciality.equals(speciality);
        }
        return false;
    }

    public Boolean updateSpecialityName(SpecialityUpdateNameDto specialityUpdateNameDto) {
        Optional<Speciality> optionalSpeciality = specialityRepository.findById(specialityUpdateNameDto.getId());
        if (optionalSpeciality.isPresent()) {
            Speciality speciality = optionalSpeciality.get();
            speciality.setName(specialityUpdateNameDto.getName());
            Speciality savedSpeciality = specialityRepository.save(speciality);
            return savedSpeciality.equals(speciality);
        }
        return false;
    }

    public Boolean updateSpecialityFaculty(SpecialityUpdateFacultyDto specialityUpdateFacultyDto) {
        Optional<Speciality> optionalSpeciality = specialityRepository.findById(specialityUpdateFacultyDto.getId());
        if (optionalSpeciality.isPresent()) {
            Speciality speciality = optionalSpeciality.get();
            if (facultyRepository.findByName(specialityUpdateFacultyDto.getFaculty()).isPresent()) {
                speciality.setFacultyId(facultyRepository.findByName(specialityUpdateFacultyDto.getFaculty()).get().getId());
            }
            Speciality savedSpeciality = specialityRepository.save(speciality);
            return savedSpeciality.equals(speciality);
        }
        return false;
    }

    public Boolean deleteSpeciality(Long id) {
        Optional<Speciality> optionalSpeciality = specialityRepository.findById(id);
        if (optionalSpeciality.isEmpty()) {
            return false;
        }
        specialityRepository.delete(optionalSpeciality.get());
        return true;
    }
}
