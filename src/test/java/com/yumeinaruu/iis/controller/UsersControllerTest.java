package com.yumeinaruu.iis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yumeinaruu.iis.model.Users;
import com.yumeinaruu.iis.model.dto.group.GroupForUsersDto;
import com.yumeinaruu.iis.model.dto.users.UsersCreateDto;
import com.yumeinaruu.iis.model.dto.users.UsersFindByNameDto;
import com.yumeinaruu.iis.model.dto.users.UsersUpdateDto;
import com.yumeinaruu.iis.model.dto.users.UsersUpdateGroupDto;
import com.yumeinaruu.iis.model.dto.users.UsersUsernameUpdateDto;
import com.yumeinaruu.iis.security.filter.JwtFilter;
import com.yumeinaruu.iis.service.UsersService;
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
@WebMvcTest(value = UsersController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UsersControllerTest {
    @MockBean
    private JwtFilter jwtAuthenticationFilter;

    @MockBean
    UsersService usersService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    static List<Users> users = new ArrayList<>();
    static Users user = new Users();
    static UsersCreateDto usersCreateDto = new UsersCreateDto();
    static UsersUpdateDto usersUpdateDto = new UsersUpdateDto();
    static Optional<Users> usersOptional = Optional.of(user);
    static UsersUsernameUpdateDto usersUsernameUpdateDto = new UsersUsernameUpdateDto();
    static UsersUpdateGroupDto usersUpdateGroupDto = new UsersUpdateGroupDto();
    static GroupForUsersDto groupForUsersDto = new GroupForUsersDto();
    static UsersFindByNameDto usersFindByNameDto = new UsersFindByNameDto();


    @BeforeAll
    public static void beforeAll() {
        usersFindByNameDto.setUsername("test_name");
        groupForUsersDto.setName("GroupForUsers");
        usersUpdateGroupDto.setId(5L);
        usersUpdateGroupDto.setGroup(groupForUsersDto);
        usersUpdateDto.setUsername("test_name");
        usersUpdateDto.setGroup(groupForUsersDto);
        usersUpdateDto.setId(5L);
        usersUsernameUpdateDto.setId(5L);
        usersUsernameUpdateDto.setUsername("test_name");
        usersCreateDto.setGroup(groupForUsersDto);
        usersCreateDto.setUsername("test_name");
        user.setId(5L);
        user.setUsername("test_name");
        user.setChanged(Timestamp.valueOf(LocalDateTime.of(2025, 12, 12, 12, 12, 12)));
        user.setCreated(Timestamp.valueOf(LocalDateTime.of(2025, 12, 12, 12, 12, 12)));
        users.add(user);
    }

    @Test
    void getAllUsers_OK() throws Exception {
        Mockito.when(usersService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(5)));
    }

    @Test
    void getAllUsers_NotFound() throws Exception {
        Mockito.when(usersService.getAllUsers()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/user"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllUsersSortedByName_OK() throws Exception {
        Mockito.when(usersService.getUsersSortedByUsername()).thenReturn(users);

        mockMvc.perform(get("/user/name-sort"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(5)));
    }

    @Test
    void getAllUsersSortedByName_NotFound() throws Exception {
        Mockito.when(usersService.getUsersSortedByUsername()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/user/name-sort"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUserById_OK() throws Exception {
        Mockito.when(usersService.getUserById(anyLong())).thenReturn(usersOptional);

        mockMvc.perform(get("/user/id/{id}", 5L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(5)));
    }

    @Test
    void getUserById_NotFound() throws Exception {
        Mockito.when(usersService.getUserById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/user/id/{id}", 5L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUserByName_OK() throws Exception {
        Mockito.when(usersService.getUserByUsername(any())).thenReturn(usersOptional);

        mockMvc.perform(post("/user/name")
                        .content(objectMapper.writeValueAsBytes(usersFindByNameDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", Matchers.is("test_name")));
    }

    @Test
    void getUserByName_NotFound() throws Exception {
        Mockito.when(usersService.getUserByUsername(any())).thenReturn(Optional.empty());

        mockMvc.perform(get("/group/name")
                        .content(objectMapper.writeValueAsBytes(usersFindByNameDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createUser_Success() throws Exception {
        Mockito.when(usersService.createUser(any())).thenReturn(true);

        mockMvc.perform(post("/user")
                        .content(objectMapper.writeValueAsBytes(usersCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void createUser_IsConflict() throws Exception {
        Mockito.when(usersService.createUser(any())).thenReturn(false);

        mockMvc.perform(post("/user")
                        .content(objectMapper.writeValueAsBytes(usersCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void updateUser_IsNoContent() throws Exception {
        Mockito.when(usersService.updateUser(any())).thenReturn(true);

        mockMvc.perform(put("/user")
                        .content(objectMapper.writeValueAsBytes(usersUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateUser_IsConflict() throws Exception {
        Mockito.when(usersService.updateUser(any())).thenReturn(false);

        mockMvc.perform(put("/user")
                        .content(objectMapper.writeValueAsBytes(usersUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void updateUserUsername_IsNoContent() throws Exception {
        Mockito.when(usersService.updateUsersUsername(any())).thenReturn(true);

        mockMvc.perform(put("/user/name")
                        .content(objectMapper.writeValueAsBytes(usersUsernameUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateUserUsername_IsConflict() throws Exception {
        Mockito.when(usersService.updateUsersUsername(any())).thenReturn(false);

        mockMvc.perform(put("/user/name")
                        .content(objectMapper.writeValueAsBytes(usersUsernameUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void updateUserGroup_IsNoContent() throws Exception {
        Mockito.when(usersService.updateUsersGroup(any())).thenReturn(true);

        mockMvc.perform(put("/user/group")
                        .content(objectMapper.writeValueAsBytes(usersUpdateGroupDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateUserGroup_IsConflict() throws Exception {
        Mockito.when(usersService.updateUsersGroup(any())).thenReturn(false);

        mockMvc.perform(put("/user/group")
                        .content(objectMapper.writeValueAsBytes(usersUpdateGroupDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }


    @Test
    void deleteUserById_IsNoContent() throws Exception {
        Mockito.when(usersService.deleteUser(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/user/5"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUserById_IsConflict() throws Exception {
        Mockito.when(usersService.deleteUser(anyLong())).thenReturn(false);

        mockMvc.perform(delete("/user/10"))
                .andExpect(status().isConflict());
    }
}
