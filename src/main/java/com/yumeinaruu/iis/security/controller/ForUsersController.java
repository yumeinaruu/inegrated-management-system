package com.yumeinaruu.iis.security.controller;

import com.yumeinaruu.iis.model.Users;
import com.yumeinaruu.iis.security.service.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "For getting additional info with roles etc.", description = "Methods to work partly with security")
@RequestMapping("/find")
@SecurityRequirement(name = "Bearer Authentication")
public class ForUsersController {
    private final SecurityService securityService;

    @Autowired
    public ForUsersController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Operation(summary = "Gives info about all students")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Students info has returned successfully"),
            @ApiResponse(responseCode = "404", description = "Students not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @GetMapping("/students")
    public ResponseEntity<List<Users>> getAllStudents() {
        List<Users> students = securityService.getAllStudents();
        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @Operation(summary = "Gives info about all teachers")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Teachers info has returned successfully"),
            @ApiResponse(responseCode = "404", description = "Teachers not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @GetMapping("/teachers")
    public ResponseEntity<List<Users>> getAllTeachers() {
        List<Users> teachers = securityService.getAllTeachers();
        if (teachers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }
}
