package com.yumeinaruu.iis.controller;

import com.yumeinaruu.iis.exception.custom_exception.CustomValidationException;
import com.yumeinaruu.iis.model.Users;
import com.yumeinaruu.iis.model.dto.users.UsersCreateDto;
import com.yumeinaruu.iis.model.dto.users.UsersFindByNameDto;
import com.yumeinaruu.iis.model.dto.users.UsersUpdateDto;
import com.yumeinaruu.iis.model.dto.users.UsersUpdateGroupDto;
import com.yumeinaruu.iis.model.dto.users.UsersUsernameUpdateDto;
import com.yumeinaruu.iis.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Work with users", description = "Methods to work with users")
@RequestMapping("/user")
@SecurityRequirement(name = "Bearer Authentication")
public class UsersController {
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @Operation(summary = "Gives info about all users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User info has returned successfully"),
            @ApiResponse(responseCode = "404", description = "Users not found"),
            @ApiResponse(responseCode = "401", description = "Username not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = usersService.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Gives info about user by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User info has returned successfully"),
            @ApiResponse(responseCode = "404", description = "Users not found"),
            @ApiResponse(responseCode = "401", description = "Username not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        Optional<Users> user = usersService.getUserById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Gives info about user by name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User info has returned successfully"),
            @ApiResponse(responseCode = "404", description = "Users not found"),
            @ApiResponse(responseCode = "401", description = "Username not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PostMapping("/name")
    public ResponseEntity<Users> getUserByName(@RequestBody @Valid UsersFindByNameDto name,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        Optional<Users> user = usersService.getUserByUsername(name.getUsername());
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Gives info about all users sorted by name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User info has returned successfully"),
            @ApiResponse(responseCode = "404", description = "Users not found"),
            @ApiResponse(responseCode = "401", description = "Username not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @GetMapping("/name-sort")
    public ResponseEntity<List<Users>> getUsersSortedByUsername() {
        List<Users> users = usersService.getUsersSortedByUsername();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Gives info about current user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User info has returned successfully"),
            @ApiResponse(responseCode = "404", description = "Users not found"),
            @ApiResponse(responseCode = "401", description = "Username not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @GetMapping("/info")
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<Users> getCurrentUser(Principal principal) {
        Optional<Users> result = usersService.getInfoAboutCurrentUser(principal.getName());
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result.get(), HttpStatus.OK);
    }

    @Operation(summary = "Creates user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User is created successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> createUser(@RequestBody @Valid UsersCreateDto usersCreateDto,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(usersService.createUser(usersCreateDto) ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Updates user")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User update successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid UsersUpdateDto usersUpdateDto,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(usersService.updateUser(usersUpdateDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Updates user's username")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User's username update successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PutMapping("/name")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> updateUserUsername(@RequestBody @Valid UsersUsernameUpdateDto usersUsernameUpdateDto,
                                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(usersService.updateUsersUsername(usersUsernameUpdateDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Updates user's group")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User's group update successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PutMapping("/group")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> updateUserGroup(@RequestBody @Valid UsersUpdateGroupDto usersUpdateGroupDto,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(usersService.updateUsersGroup(usersUpdateGroupDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Deletes user")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User is deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable Long id) {
        return new ResponseEntity<>(usersService.deleteUser(id) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }
}
