package com.yumeinaruu.iis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yumeinaruu.iis.model.Faculty;
import com.yumeinaruu.iis.model.dto.faculty.FacultyCreateDto;
import com.yumeinaruu.iis.model.dto.faculty.FacultyUpdateDto;
import com.yumeinaruu.iis.security.filter.JwtFilter;
import com.yumeinaruu.iis.service.FacultyService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = FacultyController.class)
@AutoConfigureMockMvc(addFilters = false)
public class FacultyControllerTest {
    @MockBean
    private JwtFilter jwtAuthenticationFilter;

    @MockBean
    FacultyService facultyService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    static List<Faculty> faculties = new ArrayList<>();
    static Faculty faculty = new Faculty();
    static FacultyCreateDto facultyCreateDto = new FacultyCreateDto();
    static FacultyUpdateDto facultyUpdateDto = new FacultyUpdateDto();
    static Optional<Faculty> facultyOptional = Optional.of(faculty);

    @BeforeAll
    public static void beforeAll() {
        facultyCreateDto.setName("test_name");
        facultyUpdateDto.setName("test_name");
        facultyUpdateDto.setId(5L);
        faculty.setId(5L);
        faculty.setName("test_name");
        faculties.add(faculty);
    }

    @Test
    void getAllFaculties_OK() throws Exception {
        Mockito.when(facultyService.getAllFaculties()).thenReturn(faculties);

        mockMvc.perform(get("/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(5)));
    }

    @Test
    void getAllFaculties_NotFound() throws Exception {
        Mockito.when(facultyService.getAllFaculties()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/faculty"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllFacultiesSortedByName_OK() throws Exception {
        Mockito.when(facultyService.getFacultiesSortedByName()).thenReturn(faculties);

        mockMvc.perform(get("/faculty/name-sort"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(5)));
    }

    @Test
    void getAllFacultiesSortedByName_NotFound() throws Exception {
        Mockito.when(facultyService.getFacultiesSortedByName()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/faculty/name-sort"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getFacultyById_OK() throws Exception {
        Mockito.when(facultyService.getFacultyById(anyLong())).thenReturn(facultyOptional);

        mockMvc.perform(get("/faculty/id/{id}", 5L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(5)));
    }

    @Test
    void getFacultyById_NotFound() throws Exception {
        Mockito.when(facultyService.getFacultyById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/faculty/id/{id}", 5L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getFacultyByName_OK() throws Exception {
        Mockito.when(facultyService.getFacultyByName(any())).thenReturn(facultyOptional);

        mockMvc.perform(get("/faculty/name/{name}", "test_name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("test_name")));
    }

    @Test
    void getFacultyByName_NotFound() throws Exception {
        Mockito.when(facultyService.getFacultyByName(any())).thenReturn(Optional.empty());

        mockMvc.perform(get("/faculty/name/{name}", "test_name"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createFaculty_Success() throws Exception {
        Mockito.when(facultyService.createFaculty(any())).thenReturn(true);

        mockMvc.perform(post("/faculty")
                        .content(objectMapper.writeValueAsBytes(facultyCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void createFaculty_IsConflict() throws Exception {
        Mockito.when(facultyService.createFaculty(any())).thenReturn(false);

        mockMvc.perform(post("/faculty")
                        .content(objectMapper.writeValueAsBytes(facultyCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void updateFaculty_IsNoContent() throws Exception {
        Mockito.when(facultyService.updateFaculty(any())).thenReturn(true);

        mockMvc.perform(put("/faculty")
                        .content(objectMapper.writeValueAsBytes(facultyUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateFaculty_IsConflict() throws Exception {
        Mockito.when(facultyService.updateFaculty(any())).thenReturn(false);

        mockMvc.perform(put("/faculty")
                        .content(objectMapper.writeValueAsBytes(facultyUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void deleteFaculty_IsNoContent() throws Exception {
        Mockito.when(facultyService.deleteFaculty(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/faculty/5"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteFaculty_IsConflict() throws Exception {
        Mockito.when(facultyService.deleteFaculty(anyLong())).thenReturn(false);

        mockMvc.perform(delete("/faculty/10"))
                .andExpect(status().isConflict());
    }
}
