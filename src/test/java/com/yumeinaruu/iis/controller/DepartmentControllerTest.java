package com.yumeinaruu.iis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yumeinaruu.iis.model.Department;
import com.yumeinaruu.iis.model.dto.department.DepartmentDtoCreate;
import com.yumeinaruu.iis.model.dto.department.DepartmentDtoUpdate;
import com.yumeinaruu.iis.security.filter.JwtFilter;
import com.yumeinaruu.iis.service.DepartmentService;
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
import static org.mockito.ArgumentMatchers.notNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = DepartmentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DepartmentControllerTest {
    @MockBean
    private JwtFilter jwtAuthenticationFilter;

    @MockBean
    DepartmentService departmentService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    static List<Department> departments = new ArrayList<>();
    static Department department = new Department();
    static DepartmentDtoCreate departmentDtoCreate = new DepartmentDtoCreate();
    static DepartmentDtoUpdate departmentDtoUpdate = new DepartmentDtoUpdate();
    static Optional<Department> departmentOptional = Optional.of(department);

    @BeforeAll
    public static void beforeAll() {
        departmentDtoCreate.setName("test_name");
        departmentDtoUpdate.setName("test_name");
        departmentDtoUpdate.setId(5L);
        department.setId(5L);
        department.setName("test_name");
        departments.add(department);
    }

    @Test
    void getAllDepartments_OK() throws Exception {
        Mockito.when(departmentService.getAllDepartments()).thenReturn(departments);

        mockMvc.perform(get("/department"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(5)));
    }

    @Test
    void getAllDepartments_NotFound() throws Exception {
        Mockito.when(departmentService.getAllDepartments()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/department"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllDepartmentsSortedByName_OK() throws Exception {
        Mockito.when(departmentService.getDepartmentsSortedByName()).thenReturn(departments);

        mockMvc.perform(get("/department/name-sort"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(5)));
    }

    @Test
    void getAllDepartmentsSortedByName_NotFound() throws Exception {
        Mockito.when(departmentService.getDepartmentsSortedByName()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/department/name-sort"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getDepartmentById_OK() throws Exception {
        Mockito.when(departmentService.getDepartmentById(anyLong())).thenReturn(departmentOptional);

        mockMvc.perform(get("/department/id/{id}", 5L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(5)));
    }

    @Test
    void getDepartmentById_NotFound() throws Exception {
        Mockito.when(departmentService.getDepartmentById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/department/id/{id}", 5L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getDepartmentByName_OK() throws Exception {
        Mockito.when(departmentService.getDepartmentByName(any())).thenReturn(departmentOptional);

        mockMvc.perform(get("/department/name/{name}", "test_name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("test_name")));
    }

    @Test
    void getDepartmentByName_NotFound() throws Exception {
        Mockito.when(departmentService.getDepartmentByName(any())).thenReturn(Optional.empty());

        mockMvc.perform(get("/department/name/{name}", "test_name"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createDepartment_Success() throws Exception {
        Mockito.when(departmentService.createDepartment(any())).thenReturn(true);

        mockMvc.perform(post("/department")
                        .content(objectMapper.writeValueAsBytes(departmentDtoCreate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void createDepartment_IsConflict() throws Exception {
        Mockito.when(departmentService.createDepartment(any())).thenReturn(false);

        mockMvc.perform(post("/department")
                        .content(objectMapper.writeValueAsBytes(departmentDtoCreate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void updateDepartment_IsNoContent() throws Exception {
        Mockito.when(departmentService.updateDepartment(any())).thenReturn(true);

        mockMvc.perform(put("/department")
                        .content(objectMapper.writeValueAsBytes(departmentDtoUpdate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateDepartment_IsConflict() throws Exception {
        Mockito.when(departmentService.updateDepartment(any())).thenReturn(false);

        mockMvc.perform(put("/department")
                        .content(objectMapper.writeValueAsBytes(departmentDtoUpdate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void deleteDepartment_IsNoContent() throws Exception {
        Mockito.when(departmentService.deleteDepartment(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/department/5"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteDepartment_IsConflict() throws Exception {
        Mockito.when(departmentService.deleteDepartment(anyLong())).thenReturn(false);

        mockMvc.perform(delete("/department/10"))
                .andExpect(status().isConflict());
    }
}
