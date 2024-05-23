package com.yumeinaruu.iis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yumeinaruu.iis.model.Group;
import com.yumeinaruu.iis.model.Users;
import com.yumeinaruu.iis.model.dto.faculty.FacultyForGroupDto;
import com.yumeinaruu.iis.model.dto.group.GroupCreateDto;
import com.yumeinaruu.iis.model.dto.group.GroupFacultyUpdateDto;
import com.yumeinaruu.iis.model.dto.group.GroupNameUpdateDto;
import com.yumeinaruu.iis.model.dto.group.GroupSpecialityUpdateDto;
import com.yumeinaruu.iis.model.dto.group.GroupUpdateDto;
import com.yumeinaruu.iis.model.dto.speciality.SpecialityForGroupDto;
import com.yumeinaruu.iis.security.filter.JwtFilter;
import com.yumeinaruu.iis.service.GroupService;
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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
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
@WebMvcTest(value = GroupController.class)
@AutoConfigureMockMvc(addFilters = false)
public class GroupControllerTest {
    @MockBean
    private JwtFilter jwtAuthenticationFilter;

    @MockBean
    GroupService groupService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    static List<Group> groups = new ArrayList<>();
    static Group group = new Group();
    static GroupCreateDto groupCreateDto = new GroupCreateDto();
    static GroupUpdateDto groupUpdateDto = new GroupUpdateDto();
    static FacultyForGroupDto faculty = new FacultyForGroupDto();
    static SpecialityForGroupDto speciality = new SpecialityForGroupDto();
    static Optional<Group> groupOptional = Optional.of(group);
    static GroupNameUpdateDto groupNameUpdateDto = new GroupNameUpdateDto();
    static GroupFacultyUpdateDto groupFacultyUpdateDto = new GroupFacultyUpdateDto();
    static FacultyForGroupDto facultyForGroupDto = new FacultyForGroupDto();
    static GroupSpecialityUpdateDto groupSpecialityUpdateDto = new GroupSpecialityUpdateDto();
    static SpecialityForGroupDto specialityForGroupDto = new SpecialityForGroupDto();

    @BeforeAll
    public static void beforeAll() {
        specialityForGroupDto.setName("specialityForGroupDto");
        groupSpecialityUpdateDto.setId(5L);
        groupSpecialityUpdateDto.setSpeciality(specialityForGroupDto);
        facultyForGroupDto.setName("facultyForGroupDto");
        groupFacultyUpdateDto.setId(5L);
        groupFacultyUpdateDto.setFaculty(facultyForGroupDto);
        faculty.setName("test_name");
        speciality.setName("test_name");
        groupCreateDto.setName("test_name");
        groupCreateDto.setFaculty(faculty);
        groupCreateDto.setSpeciality(speciality);
        groupUpdateDto.setName("test_name");
        groupUpdateDto.setFaculty(faculty);
        groupUpdateDto.setSpeciality(speciality);
        groupUpdateDto.setId(5L);
        groupNameUpdateDto.setId(5L);
        groupNameUpdateDto.setName("test_update_name");
        group.setId(5L);
        group.setName("test_name");
        groups.add(group);
    }

    @Test
    void getAllGroups_OK() throws Exception {
        Mockito.when(groupService.getAllGroups()).thenReturn(groups);

        mockMvc.perform(get("/group"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(5)));
    }

    @Test
    void getAllGroups_NotFound() throws Exception {
        Mockito.when(groupService.getAllGroups()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/group"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllGroupsSortedByName_OK() throws Exception {
        Mockito.when(groupService.getGroupsSortedByName()).thenReturn(groups);

        mockMvc.perform(get("/group/name-sort"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(5)));
    }

    @Test
    void getAllGroupsSortedByName_NotFound() throws Exception {
        Mockito.when(groupService.getGroupsSortedByName()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/group/name-sort"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getGroupById_OK() throws Exception {
        Mockito.when(groupService.getGroupById(anyLong())).thenReturn(groupOptional);

        mockMvc.perform(get("/group/id/{id}", 5L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(5)));
    }

    @Test
    void getGroupById_NotFound() throws Exception {
        Mockito.when(groupService.getGroupById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/group/id/{id}", 5L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getGroupByName_OK() throws Exception {
        Mockito.when(groupService.getGroupByName(any())).thenReturn(groupOptional);

        mockMvc.perform(get("/group/name/{name}", "test_name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("test_name")));
    }

    @Test
    void getGroupByName_NotFound() throws Exception {
        Mockito.when(groupService.getGroupByName(any())).thenReturn(Optional.empty());

        mockMvc.perform(get("/group/name/{name}", "test_name"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createGroup_Success() throws Exception {
        Mockito.when(groupService.createGroup(any())).thenReturn(true);

        mockMvc.perform(post("/group")
                        .content(objectMapper.writeValueAsBytes(groupCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void createGroup_IsConflict() throws Exception {
        Mockito.when(groupService.createGroup(any())).thenReturn(false);

        mockMvc.perform(post("/group")
                        .content(objectMapper.writeValueAsBytes(groupCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void updateGroup_IsNoContent() throws Exception {
        Mockito.when(groupService.updateGroup(any())).thenReturn(true);

        mockMvc.perform(put("/group")
                        .content(objectMapper.writeValueAsBytes(groupUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateGroup_IsConflict() throws Exception {
        Mockito.when(groupService.updateGroup(any())).thenReturn(false);

        mockMvc.perform(put("/group")
                        .content(objectMapper.writeValueAsBytes(groupUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void updateGroupName_IsNoContent() throws Exception {
        Mockito.when(groupService.updateName(any())).thenReturn(true);

        mockMvc.perform(put("/group/name")
                        .content(objectMapper.writeValueAsBytes(groupNameUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateGroupName_IsConflict() throws Exception {
        Mockito.when(groupService.updateName(any())).thenReturn(false);

        mockMvc.perform(put("/group/name")
                        .content(objectMapper.writeValueAsBytes(groupNameUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void updateGroupFaculty_IsNoContent() throws Exception {
        Mockito.when(groupService.updateFaculty(any())).thenReturn(true);

        mockMvc.perform(put("/group/faculty")
                        .content(objectMapper.writeValueAsBytes(groupFacultyUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateGroupFaculty_IsConflict() throws Exception {
        Mockito.when(groupService.updateFaculty(any())).thenReturn(false);

        mockMvc.perform(put("/group/faculty")
                        .content(objectMapper.writeValueAsBytes(groupFacultyUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void updateGroupSpeciality_IsNoContent() throws Exception {
        Mockito.when(groupService.updateSpeciality(any())).thenReturn(true);

        mockMvc.perform(put("/group/speciality")
                        .content(objectMapper.writeValueAsBytes(groupSpecialityUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateGroupSpeciality_IsConflict() throws Exception {
        Mockito.when(groupService.updateSpeciality(any())).thenReturn(false);

        mockMvc.perform(put("/group/speciality")
                        .content(objectMapper.writeValueAsBytes(groupSpecialityUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void deleteGroup_IsNoContent() throws Exception {
        Mockito.when(groupService.deleteGroup(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/group/5"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteGroup_IsConflict() throws Exception {
        Mockito.when(groupService.deleteGroup(anyLong())).thenReturn(false);

        mockMvc.perform(delete("/group/10"))
                .andExpect(status().isConflict());
    }
}
