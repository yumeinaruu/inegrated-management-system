package com.yumeinaruu.iis.service;

import com.yumeinaruu.iis.model.Subject;
import com.yumeinaruu.iis.model.dto.subject.SubjectCreateDto;
import com.yumeinaruu.iis.model.dto.subject.SubjectDepartmentUpdateDto;
import com.yumeinaruu.iis.model.dto.subject.SubjectNameUpdateDto;
import com.yumeinaruu.iis.model.dto.subject.SubjectUpdateDto;
import com.yumeinaruu.iis.repository.DepartmentRepository;
import com.yumeinaruu.iis.repository.SubjectRepository;
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
public class SubjectServiceTest {
    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private SubjectService subjectService;

    static Subject subject = new Subject();
    static SubjectUpdateDto subjectUpdateDto = new SubjectUpdateDto();
    static SubjectCreateDto subjectCreateDto = new SubjectCreateDto();
    static SubjectNameUpdateDto subjectNameUpdateDto = new SubjectNameUpdateDto();
    static SubjectDepartmentUpdateDto subjectDepartmentUpdateDto = new SubjectDepartmentUpdateDto();

    @BeforeAll
    public static void beforeAll() {
        subjectNameUpdateDto.setId(5L);
        subjectNameUpdateDto.setName("test_name");
        subjectDepartmentUpdateDto.setId(5L);
        subjectDepartmentUpdateDto.setDepartment("test_name");
        subject.setId(5L);
        subject.setName("test_name");
        subject.setDepartmentId(1L);
        subjectCreateDto.setName("test_name");
        subjectCreateDto.setDepartment("test_name");
        subjectUpdateDto.setId(5L);
        subjectUpdateDto.setDepartment("test_name");
        subjectUpdateDto.setName("test_name");
    }

    @Test
    void getAllSubjectsTest_Success() {
        subjectService.getAllSubjects();
        Mockito.verify(subjectRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getAllSubjectsTest_Failure() {
        Mockito.when(subjectRepository.findAll()).thenReturn(new ArrayList<>());
        subjectService.getAllSubjects();
        Mockito.verify(subjectRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getSubjectById_Success() {
        subjectService.getSubjectById(5L);
        Mockito.verify(subjectRepository, Mockito.times(1)).findById(anyLong());
    }

    @Test
    void getSubjectById_Failure() {
        Mockito.when(subjectRepository.findById(anyLong())).thenReturn(Optional.empty());
        subjectService.getSubjectById(5L);
        Mockito.verify(subjectRepository, Mockito.times(1)).findById(anyLong());
    }

    @Test
    void getSubjectByName_Success() {
        subjectService.getSubjectByName("test_name");
        Mockito.verify(subjectRepository, Mockito.times(1)).findByName(any());
    }

    @Test
    void getSubjectByName_Failure() {
        Mockito.when(subjectRepository.findByName(any())).thenReturn(Optional.empty());
        subjectService.getSubjectByName("test_name");
        Mockito.verify(subjectRepository, Mockito.times(1)).findByName(any());
    }

    @Test
    void getSubjectSortedByName_Success() {
        subjectService.getSubjectsSortedByName();
        Mockito.verify(subjectRepository, Mockito.times(1)).findAll(Sort.by("name"));
    }

    @Test
    void getSubjectSortedByName_Failure() {
        Mockito.when(subjectRepository.findAll(Sort.by("name"))).thenReturn(new ArrayList<>());
        subjectService.getSubjectsSortedByName();
        Mockito.verify(subjectRepository, Mockito.times(1)).findAll(Sort.by("name"));
    }

    @Test
    void createSubjectTest_Success() {
        Mockito.when(subjectRepository.save(any())).thenReturn(subject);
        subjectService.createSubject(subjectCreateDto);

        Mockito.verify(subjectRepository, Mockito.times(1)).save(any());
    }

    @Test
    void updateSubjectTest_Success() {
        Mockito.when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));
        Mockito.when(subjectRepository.saveAndFlush(any())).thenReturn(subject);
        subjectService.updateSubject(subjectUpdateDto);

        Mockito.verify(subjectRepository, Mockito.times(1)).saveAndFlush(any());
    }

    @Test
    void updateSubjectName_Success() {
        Mockito.when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));
        Mockito.when(subjectRepository.saveAndFlush(any())).thenReturn(subject);
        subjectService.updateSubjectName(subjectNameUpdateDto);
        Mockito.verify(subjectRepository, Mockito.times(1)).saveAndFlush(any());
    }

    @Test
    void updateSubjectDepartment_Success() {
        Mockito.when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));
        Mockito.when(subjectRepository.saveAndFlush(any())).thenReturn(subject);
        subjectService.updateSubjectDepartment(subjectDepartmentUpdateDto);
        Mockito.verify(subjectRepository, Mockito.times(1)).saveAndFlush(any());
    }

    @Test
    void deleteSpecialityByIdTest_Success() {
        Mockito.when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));
        subjectService.deleteSubject(anyLong());

        Mockito.verify(subjectRepository, Mockito.times(1)).delete(subject);
    }
}
