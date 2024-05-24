package com.yumeinaruu.iis.service;

import com.yumeinaruu.iis.model.Marks;
import com.yumeinaruu.iis.model.Subject;
import com.yumeinaruu.iis.model.Users;
import com.yumeinaruu.iis.model.dto.marks.MarksCreateDto;
import com.yumeinaruu.iis.model.dto.marks.MarksUpdateDto;
import com.yumeinaruu.iis.model.dto.users.UsersForMarkDto;
import com.yumeinaruu.iis.repository.MarksRepository;
import com.yumeinaruu.iis.repository.SubjectRepository;
import com.yumeinaruu.iis.repository.UsersRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class MarksServiceTest {
    @Mock
    private MarksRepository marksRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private MarksService marksService;

    static Marks marks = new Marks();
    static MarksUpdateDto marksUpdateDto = new MarksUpdateDto();
    static UsersForMarkDto usersForMarkDto = new UsersForMarkDto();
    static MarksCreateDto marksCreateDto = new MarksCreateDto();
    static Users users = new Users();
    static Subject subject = new Subject();

    @BeforeAll
    public static void beforeAll() {
        users.setId(5L);
        users.setUsername("test_name");
        users.setChanged(Timestamp.valueOf(LocalDateTime.of(2025, 12, 12, 12, 12, 12)));
        users.setCreated(Timestamp.valueOf(LocalDateTime.of(2025, 12, 12, 12, 12, 12)));
        subject.setId(1L);
        subject.setName("test_subject");
        subject.setDepartmentId(1L);
        usersForMarkDto.setUsername("test_name");
        marksCreateDto.setUser(usersForMarkDto);
        marksCreateDto.setMark(1);
        marksCreateDto.setSubject("test_name");
        marksUpdateDto.setId(5L);
        marksUpdateDto.setMark(5);
        marksUpdateDto.setSubject("test_name");
        marksUpdateDto.setUser(usersForMarkDto);
        marks.setMark(10);
        marks.setSubjectId(1L);
        marks.setId(5L);
    }

    @Test
    void getAllMarksTest_Success() {
        marksService.getAllMarks();
        Mockito.verify(marksRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getAllMarksTest_Failure() {
        Mockito.when(marksRepository.findAll()).thenReturn(new ArrayList<>());
        marksService.getAllMarks();
        Mockito.verify(marksRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getMarksById_Success() {
        marksService.getMarkById(5L);
        Mockito.verify(marksRepository, Mockito.times(1)).findById(anyLong());
    }

    @Test
    void getMarksById_Failure() {
        Mockito.when(marksRepository.findById(anyLong())).thenReturn(Optional.empty());
        marksService.getMarkById(5L);
        Mockito.verify(marksRepository, Mockito.times(1)).findById(anyLong());
    }

    @Test
    void getMarksAscending_Success() {
        marksService.getMarksAscending();
        Mockito.verify(marksRepository, Mockito.times(1)).findAll(Sort.by("mark")
                .ascending());
    }

    @Test
    void getMarksAscending_Failure() {
        Mockito.when(marksRepository.findAll(Sort.by("mark").ascending())).thenReturn(new ArrayList<>());
        marksService.getMarksAscending();
        Mockito.verify(marksRepository, Mockito.times(1)).findAll(any(Sort.class));
    }

    @Test
    void getMarksDescending_Success() {
        marksService.getMarksDescending();
        Mockito.verify(marksRepository, Mockito.times(1)).findAll(Sort.by("mark").descending());
    }

    @Test
    void getMarksDescending_Failure() {
        Mockito.when(marksRepository.findAll(Sort.by("mark").descending())).thenReturn(new ArrayList<>());
        marksService.getMarksDescending();
        Mockito.verify(marksRepository, Mockito.times(1)).findAll(any(Sort.class));
    }

    @Test
    void createMarkTest_Success() {
        Mockito.when(marksRepository.save(any())).thenReturn(marks);
        marksService.createMark(marksCreateDto);

        Mockito.verify(marksRepository, Mockito.times(1)).save(any());
    }

    @Test
    void updateMarkTest_Success() {
        Mockito.when(marksRepository.findById(anyLong())).thenReturn(Optional.of(marks));
        Mockito.when(marksRepository.saveAndFlush(any())).thenReturn(marks);
        marksService.updateMark(marksUpdateDto);

        Mockito.verify(marksRepository, Mockito.times(1)).saveAndFlush(any());
    }

    @Test
    void deleteMarkByIdTest_Success() {
        Mockito.when(marksRepository.findById(anyLong())).thenReturn(Optional.of(marks));
        marksService.deleteMarkById(anyLong());

        Mockito.verify(marksRepository, Mockito.times(1)).delete(marks);
    }
}
