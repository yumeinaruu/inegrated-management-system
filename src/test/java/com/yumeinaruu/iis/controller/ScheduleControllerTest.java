package com.yumeinaruu.iis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yumeinaruu.iis.model.Schedule;
import com.yumeinaruu.iis.model.dto.schedule.ScheduleCreateDto;
import com.yumeinaruu.iis.model.dto.schedule.ScheduleUpdateDto;
import com.yumeinaruu.iis.model.dto.schedule.ScheduleUpdateGroupDto;
import com.yumeinaruu.iis.model.dto.schedule.ScheduleUpdateSubjectDto;
import com.yumeinaruu.iis.model.dto.schedule.ScheduleUpdateTimeDto;
import com.yumeinaruu.iis.security.filter.JwtFilter;
import com.yumeinaruu.iis.service.ScheduleService;
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
@WebMvcTest(value = ScheduleController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ScheduleControllerTest {
    @MockBean
    private JwtFilter jwtAuthenticationFilter;

    @MockBean
    ScheduleService scheduleService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    static List<Schedule> schedules = new ArrayList<>();
    static Schedule schedule = new Schedule();
    static ScheduleCreateDto scheduleCreateDto = new ScheduleCreateDto();
    static ScheduleUpdateDto scheduleUpdateDto = new ScheduleUpdateDto();
    static Optional<Schedule> scheduleOptional = Optional.of(schedule);
    static ScheduleUpdateGroupDto scheduleUpdateGroupDto = new ScheduleUpdateGroupDto();
    static ScheduleUpdateSubjectDto scheduleUpdateSubjectDto = new ScheduleUpdateSubjectDto();
    static ScheduleUpdateTimeDto scheduleUpdateTimeDto = new ScheduleUpdateTimeDto();


    @BeforeAll
    public static void beforeAll() {
        scheduleUpdateGroupDto.setId(5L);
        scheduleUpdateGroupDto.setGroup("test_name");
        scheduleCreateDto.setGroup("test_name");
        scheduleCreateDto.setSubject("test_name");
        scheduleCreateDto.setBeginning(Timestamp.valueOf(LocalDateTime.of(2025, 12, 12, 12, 12, 12)));
        scheduleCreateDto.setEnding(Timestamp.valueOf(LocalDateTime.of(2025, 12, 12, 12, 12, 12)));
        scheduleUpdateDto.setId(5L);
        scheduleUpdateDto.setGroup("test_name");
        scheduleUpdateDto.setSubject("test_name");
        scheduleUpdateDto.setBeginning(Timestamp.valueOf(LocalDateTime.of(2025, 12, 12, 12, 12, 12)));
        scheduleUpdateDto.setEnding(Timestamp.valueOf(LocalDateTime.of(2025, 12, 12, 12, 12, 12)));
        scheduleUpdateSubjectDto.setId(5L);
        scheduleUpdateSubjectDto.setSubject("test_name");
        scheduleUpdateTimeDto.setId(5L);
        scheduleUpdateTimeDto.setBeginning(Timestamp.valueOf(LocalDateTime.of(2025, 12, 12, 12, 12, 12)));
        scheduleUpdateTimeDto.setEnding(Timestamp.valueOf(LocalDateTime.of(2025, 12, 12, 12, 12, 12)));
        scheduleUpdateDto.setId(5L);
        schedule.setId(5L);
        schedule.setGroupId(1L);
        schedule.setSubjectId(1L);
        schedule.setBeginning(Timestamp.valueOf(LocalDateTime.now()));
        schedule.setEnding(Timestamp.valueOf(LocalDateTime.now()));
        schedules.add(schedule);
    }

    @Test
    void getAllSchedules_OK() throws Exception {
        Mockito.when(scheduleService.getAllSchedules()).thenReturn(schedules);

        mockMvc.perform(get("/schedule"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(5)));
    }

    @Test
    void getAllSchedules_NotFound() throws Exception {
        Mockito.when(scheduleService.getAllSchedules()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/schedule"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getScheduleById_OK() throws Exception {
        Mockito.when(scheduleService.getScheduleById(anyLong())).thenReturn(scheduleOptional);

        mockMvc.perform(get("/schedule/id/{id}", 5L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(5)));
    }

    @Test
    void getScheduleById_NotFound() throws Exception {
        Mockito.when(scheduleService.getScheduleById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/schedule/id/{id}", 5L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getScheduleByGroupName_OK() throws Exception {
        Mockito.when(scheduleService.getScheduleByGroupName(any())).thenReturn(schedules);

        mockMvc.perform(get("/schedule/group/{group}", "test_name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].groupId", Matchers.is(1)));
    }

    @Test
    void getScheduleByGroupName_isNotFound() throws Exception {
        Mockito.when(scheduleService.getScheduleByGroupName(any())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/schedule/group/{group}", "test_name"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getScheduleBySubject_OK() throws Exception {
        Mockito.when(scheduleService.getScheduleBySubjectName(any())).thenReturn(schedules);

        mockMvc.perform(get("/schedule/subject/{subject}", "test_name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].subjectId", Matchers.is(1)));
    }

    @Test
    void getScheduleBySubject_isNotFound() throws Exception {
        Mockito.when(scheduleService.getScheduleBySubjectName(any())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/schedule/subject/{subject}", "test_name"))
                .andExpect(status().isNotFound());
    }


    @Test
    void createSchedule_Success() throws Exception {
        Mockito.when(scheduleService.createSchedule(any())).thenReturn(true);

        mockMvc.perform(post("/schedule")
                        .content(objectMapper.writeValueAsBytes(scheduleCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void createSchedule_IsConflict() throws Exception {
        Mockito.when(scheduleService.createSchedule(any())).thenReturn(false);

        mockMvc.perform(post("/schedule")
                        .content(objectMapper.writeValueAsBytes(scheduleCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void updateSchedule_IsNoContent() throws Exception {
        Mockito.when(scheduleService.updateSchedule(any())).thenReturn(true);

        mockMvc.perform(put("/schedule")
                        .content(objectMapper.writeValueAsBytes(scheduleUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateSchedule_IsConflict() throws Exception {
        Mockito.when(scheduleService.updateSchedule(any())).thenReturn(false);

        mockMvc.perform(put("/schedule")
                        .content(objectMapper.writeValueAsBytes(scheduleUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void updateScheduleTime_IsNoContent() throws Exception {
        Mockito.when(scheduleService.scheduleUpdateTime(any())).thenReturn(true);

        mockMvc.perform(put("/schedule/time")
                        .content(objectMapper.writeValueAsBytes(scheduleUpdateTimeDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateScheduleTime_IsConflict() throws Exception {
        Mockito.when(scheduleService.scheduleUpdateTime(any())).thenReturn(false);

        mockMvc.perform(put("/schedule/time")
                        .content(objectMapper.writeValueAsBytes(scheduleUpdateTimeDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void updateScheduleSubject_IsNoContent() throws Exception {
        Mockito.when(scheduleService.scheduleUpdateSubject(any())).thenReturn(true);

        mockMvc.perform(put("/schedule/subject")
                        .content(objectMapper.writeValueAsBytes(scheduleUpdateSubjectDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateScheduleSubject_IsConflict() throws Exception {
        Mockito.when(scheduleService.scheduleUpdateSubject(any())).thenReturn(false);

        mockMvc.perform(put("/schedule/subject")
                        .content(objectMapper.writeValueAsBytes(scheduleUpdateSubjectDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void updateScheduleGroup_IsNoContent() throws Exception {
        Mockito.when(scheduleService.scheduleUpdateGroup(any())).thenReturn(true);

        mockMvc.perform(put("/schedule/group")
                        .content(objectMapper.writeValueAsBytes(scheduleUpdateGroupDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateScheduleGroup_IsConflict() throws Exception {
        Mockito.when(scheduleService.scheduleUpdateGroup(any())).thenReturn(false);

        mockMvc.perform(put("/schedule/group")
                        .content(objectMapper.writeValueAsBytes(scheduleUpdateGroupDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void deleteSchedule_IsNoContent() throws Exception {
        Mockito.when(scheduleService.deleteScheduleById(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/schedule/5"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteSchedule_IsConflict() throws Exception {
        Mockito.when(scheduleService.deleteScheduleById(anyLong())).thenReturn(false);

        mockMvc.perform(delete("/schedule/10"))
                .andExpect(status().isConflict());
    }
}
