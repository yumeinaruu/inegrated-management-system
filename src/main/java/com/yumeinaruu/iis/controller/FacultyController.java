package com.yumeinaruu.iis.controller;

import com.yumeinaruu.iis.exception.custom_exception.CustomValidationException;
import com.yumeinaruu.iis.model.Faculty;
import com.yumeinaruu.iis.model.dto.faculty.FacultyCreateDto;
import com.yumeinaruu.iis.model.dto.faculty.FacultyUpdateDto;
import com.yumeinaruu.iis.service.FacultyService;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/faculty")
@Tag(name = "Work with faculties", description = "Methods to work with faculties")
@SecurityRequirement(name = "Bearer Authentication")
public class FacultyController {
    private final FacultyService facultyService;

    @Autowired
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @Operation(summary = "Gives info about all faculties")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Faculties info has returned successfully"),
            @ApiResponse(responseCode = "404", description = "Faculties not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @GetMapping
    public ResponseEntity<List<Faculty>> getAllFaculties() {
        List<Faculty> faculties = facultyService.getAllFaculties();
        if (faculties.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(faculties, HttpStatus.OK);
    }

    @Operation(summary = "Gives info about faculties sorted by name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Faculties info has returned successfully"),
            @ApiResponse(responseCode = "404", description = "Faculties not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @GetMapping("/name-sort")
    public ResponseEntity<List<Faculty>> getAllFacultiesSortedByName() {
        List<Faculty> faculties = facultyService.getFacultiesSortedByName();
        if (faculties.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(faculties, HttpStatus.OK);
    }

    @Operation(summary = "Gives info about faculty by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Faculty info has returned successfully"),
            @ApiResponse(responseCode = "404", description = "Faculty not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable Long id) {
        Optional<Faculty> faculty = facultyService.getFacultyById(id);
        if (faculty.isPresent()) {
            return new ResponseEntity<>(faculty.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Gives info about faculty by name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Faculty info has returned successfully"),
            @ApiResponse(responseCode = "404", description = "Faculty not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<Faculty> getFacultyByName(@PathVariable String name) {
        Optional<Faculty> faculty = facultyService.getFacultyByName(name);
        if (faculty.isPresent()) {
            return new ResponseEntity<>(faculty.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Creates faculty")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Faculty was created successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> createFaculty(@RequestBody @Valid FacultyCreateDto facultyCreateDto,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(facultyService.createFaculty(facultyCreateDto) ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Updates faculty")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Faculty was updated successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> updateFaculty(@RequestBody @Valid FacultyUpdateDto facultyUpdateDto,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(facultyService.updateFaculty(facultyUpdateDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Deletes faculty")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Faculty was deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> deleteFacultyById(@PathVariable Long id) {
        return new ResponseEntity<>(facultyService.deleteFaculty(id) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }
}
