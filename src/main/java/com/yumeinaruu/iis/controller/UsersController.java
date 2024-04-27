package com.yumeinaruu.iis.controller;

import com.yumeinaruu.iis.exception.custom_exception.CustomValidationException;
import com.yumeinaruu.iis.model.Users;
import com.yumeinaruu.iis.model.dto.users.UsersCreateDto;
import com.yumeinaruu.iis.model.dto.users.UsersUpdateDto;
import com.yumeinaruu.iis.model.dto.users.UsersUpdateGroupDto;
import com.yumeinaruu.iis.model.dto.users.UsersUsernameUpdateDto;
import com.yumeinaruu.iis.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UsersController {
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = usersService.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        Optional<Users> user = usersService.getUserById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/name")
    public ResponseEntity<List<Users>> getUsersSortedByUsername() {
        List<Users> users = usersService.getUsersSortedByUsername();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createUser(@RequestBody @Valid UsersCreateDto usersCreateDto,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(usersService.createUser(usersCreateDto) ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid UsersUpdateDto usersUpdateDto,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(usersService.updateUser(usersUpdateDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @PutMapping("/name")
    public ResponseEntity<HttpStatus> updateUserUsername(@RequestBody @Valid UsersUsernameUpdateDto usersUsernameUpdateDto,
                                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(usersService.updateUsersUsername(usersUsernameUpdateDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @PutMapping("/group")
    public ResponseEntity<HttpStatus> updateUserGroup(@RequestBody @Valid UsersUpdateGroupDto usersUpdateGroupDto,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(usersService.updateUsersGroup(usersUpdateGroupDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable Long id) {
        return new ResponseEntity<>(usersService.deleteUser(id) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }
}
