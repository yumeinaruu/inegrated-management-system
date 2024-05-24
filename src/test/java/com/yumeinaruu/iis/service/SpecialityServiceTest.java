package com.yumeinaruu.iis.service;

import com.yumeinaruu.iis.model.Faculty;
import com.yumeinaruu.iis.model.Speciality;
import com.yumeinaruu.iis.model.dto.faculty.FacultyForSpecialityDto;
import com.yumeinaruu.iis.model.dto.speciality.SpecialityCreateDto;
import com.yumeinaruu.iis.model.dto.speciality.SpecialityUpdateDto;
import com.yumeinaruu.iis.model.dto.speciality.SpecialityUpdateFacultyDto;
import com.yumeinaruu.iis.model.dto.speciality.SpecialityUpdateNameDto;
import com.yumeinaruu.iis.repository.FacultyRepository;
import com.yumeinaruu.iis.repository.SpecialityRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class SpecialityServiceTest {
    @Mock
    private SpecialityRepository specialityRepository;

    @Mock
    private FacultyRepository facultyRepository;

    @InjectMocks
    private SpecialityService specialityService;

    static Speciality speciality = new Speciality();
    static SpecialityUpdateDto specialityUpdateDto = new SpecialityUpdateDto();
    static SpecialityCreateDto specialityCreateDto = new SpecialityCreateDto();
    static FacultyForSpecialityDto facultyForSpecialityDto = new FacultyForSpecialityDto();
    static SpecialityUpdateNameDto specialityUpdateNameDto = new SpecialityUpdateNameDto();
    static SpecialityUpdateFacultyDto specialityUpdateFacultyDto = new SpecialityUpdateFacultyDto();
    static Faculty faculty = new Faculty();

    @BeforeAll
    public static void beforeAll() {
        specialityUpdateFacultyDto.setId(5L);
        specialityUpdateFacultyDto.setFaculty(facultyForSpecialityDto);
        specialityUpdateNameDto.setId(5L);
        specialityUpdateNameDto.setName("test_name");
        faculty.setId(5L);
        faculty.setName("Faculty");
        speciality.setId(5L);
        speciality.setName("test_name");
        speciality.setFacultyId(faculty);
        facultyForSpecialityDto.setName("test_name");
        specialityCreateDto.setName("test_name");
        specialityCreateDto.setFaculty(facultyForSpecialityDto);
        specialityUpdateDto.setId(5L);
        specialityUpdateDto.setFaculty(facultyForSpecialityDto);
        specialityUpdateDto.setName("test_name");
    }

    @Test
    void getAllSpecialitiesTest_Success() {
        specialityService.getAllSpecialities();
        Mockito.verify(specialityRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getAllSpecialitiesTest_Failure() {
        Mockito.when(specialityRepository.findAll()).thenReturn(new ArrayList<>());
        specialityService.getAllSpecialities();
        Mockito.verify(specialityRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getSpecialityById_Success() {
        specialityService.getSpecialityById(5L);
        Mockito.verify(specialityRepository, Mockito.times(1)).findById(anyLong());
    }

    @Test
    void getSpecialityById_Failure() {
        Mockito.when(specialityRepository.findById(anyLong())).thenReturn(Optional.empty());
        specialityService.getSpecialityById(5L);
        Mockito.verify(specialityRepository, Mockito.times(1)).findById(anyLong());
    }

    @Test
    void getSpecialityByName_Success() {
        specialityService.getSpecialityByName("test_name");
        Mockito.verify(specialityRepository, Mockito.times(1)).findByName(any());
    }

    @Test
    void getSpecialityByName_Failure() {
        Mockito.when(specialityRepository.findByName(any())).thenReturn(Optional.empty());
        specialityService.getSpecialityByName("test_name");
        Mockito.verify(specialityRepository, Mockito.times(1)).findByName(any());
    }

    @Test
    void getSpecialitiesSortedByName_Success() {
        specialityService.getSpecialitiesSortedByName();
        Mockito.verify(specialityRepository, Mockito.times(1)).findAll(Sort.by("name"));
    }

    @Test
    void getSpecialitiesSortedByName_Failure() {
        Mockito.when(specialityRepository.findAll(Sort.by("name"))).thenReturn(new ArrayList<>());
        specialityService.getSpecialitiesSortedByName();
        Mockito.verify(specialityRepository, Mockito.times(1)).findAll(Sort.by("name"));
    }

    @Test
    void createSpecialityTest_Success() {
        Mockito.when(specialityRepository.save(any())).thenReturn(speciality);
        specialityService.createSpeciality(specialityCreateDto);

        Mockito.verify(specialityRepository, Mockito.times(1)).save(any());
    }

    @Test
    void updateSpecialityTest_Success() {
        Mockito.when(specialityRepository.findById(anyLong())).thenReturn(Optional.of(speciality));
        Mockito.when(specialityRepository.saveAndFlush(any())).thenReturn(speciality);
        specialityService.updateSpeciality(specialityUpdateDto);

        Mockito.verify(specialityRepository, Mockito.times(1)).saveAndFlush(any());
    }

    @Test
    void updateSpecialityName_Success() {
        Mockito.when(specialityRepository.findById(anyLong())).thenReturn(Optional.of(speciality));
        Mockito.when(specialityRepository.saveAndFlush(any())).thenReturn(speciality);
        specialityService.updateSpecialityName(specialityUpdateNameDto);
        Mockito.verify(specialityRepository, Mockito.times(1)).saveAndFlush(any());
    }

    @Test
    void updateSpecialityFaculty_Success() {
        Mockito.when(specialityRepository.findById(anyLong())).thenReturn(Optional.of(speciality));
        Mockito.when(specialityRepository.saveAndFlush(any())).thenReturn(speciality);
        specialityService.updateSpecialityFaculty(specialityUpdateFacultyDto);
        Mockito.verify(specialityRepository, Mockito.times(1)).saveAndFlush(any());
    }

    @Test
    void deleteSpecialityByIdTest_Success() {
        Mockito.when(specialityRepository.findById(anyLong())).thenReturn(Optional.of(speciality));
        specialityService.deleteSpeciality(anyLong());

        Mockito.verify(specialityRepository, Mockito.times(1)).delete(speciality);
    }
}
