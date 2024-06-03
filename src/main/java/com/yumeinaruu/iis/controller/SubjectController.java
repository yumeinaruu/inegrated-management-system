package com.yumeinaruu.iis.controller;

import com.yumeinaruu.iis.exception.custom_exception.CustomValidationException;
import com.yumeinaruu.iis.model.Subject;
import com.yumeinaruu.iis.model.dto.subject.SubjectCreateDto;
import com.yumeinaruu.iis.model.dto.subject.SubjectDepartmentUpdateDto;
import com.yumeinaruu.iis.model.dto.subject.SubjectNameUpdateDto;
import com.yumeinaruu.iis.model.dto.subject.SubjectUpdateDto;
import com.yumeinaruu.iis.service.SubjectService;
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
@Tag(name = "Work with subjects", description = "Methods to work with subjects")
@RequestMapping("/subject")
@SecurityRequirement(name = "Bearer Authentication")
public class SubjectController {
    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Operation(summary = "Gives info about all subjects")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Subject info has returned successfully"),
            @ApiResponse(responseCode = "404", description = "Subjects not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @GetMapping
    public ResponseEntity<List<Subject>> getAllSubjects() {
        List<Subject> subjects = subjectService.getAllSubjects();
        if (subjects.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    @Operation(summary = "Gives info about subject by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Subject info has returned successfully"),
            @ApiResponse(responseCode = "404", description = "Subjects not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<Subject> getSubjectById(@PathVariable Long id) {
        Optional<Subject> subject = subjectService.getSubjectById(id);
        if (subject.isPresent()) {
            return new ResponseEntity<>(subject.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Gives info about subject by name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Subject info has returned successfully"),
            @ApiResponse(responseCode = "404", description = "Subjects not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<Subject> getSubjectByName(@PathVariable String name) {
        Optional<Subject> subject = subjectService.getSubjectByName(name);
        if (subject.isPresent()) {
            return new ResponseEntity<>(subject.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Gives info about all subjects sorted by name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Subject info has returned successfully"),
            @ApiResponse(responseCode = "404", description = "Subjects not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @GetMapping("/name-sorted")
    public ResponseEntity<List<Subject>> getSubjectsSortedByName() {
        List<Subject> subjects = subjectService.getSubjectsSortedByName();
        if (subjects.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    @Operation(summary = "Creates subject")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Subject was created successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> createSubject(@RequestBody @Valid SubjectCreateDto subjectCreateDto,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(subjectService.createSubject(subjectCreateDto) ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Updates info about subjects")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Subject info was updated successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> updateSubject(@RequestBody @Valid SubjectUpdateDto subjectUpdateDto,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(subjectService.updateSubject(subjectUpdateDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Updates subject's name")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Subject info was updated successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PutMapping("/name")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> updateSubjectName(@RequestBody @Valid SubjectNameUpdateDto subjectNameUpdateDto,
                                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(subjectService.updateSubjectName(subjectNameUpdateDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Updates subject's department")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Subject info was updated successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PutMapping("/department")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> updateSubjectDepartment(@RequestBody @Valid SubjectDepartmentUpdateDto subjectDepartmentUpdateDto,
                                                              BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(subjectService.updateSubjectDepartment(subjectDepartmentUpdateDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Deletes subject")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Subject was deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> deleteSubjectById(@PathVariable Long id) {
        return new ResponseEntity<>(subjectService.deleteSubject(id) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }
}
