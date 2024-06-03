package com.yumeinaruu.iis.controller;

import com.yumeinaruu.iis.exception.custom_exception.CustomValidationException;
import com.yumeinaruu.iis.model.Marks;
import com.yumeinaruu.iis.model.dto.marks.MarksCreateDto;
import com.yumeinaruu.iis.model.dto.marks.MarksMarkUpdateDto;
import com.yumeinaruu.iis.model.dto.marks.MarksSubjectUpdateDto;
import com.yumeinaruu.iis.model.dto.marks.MarksUpdateDto;
import com.yumeinaruu.iis.model.dto.marks.MarksUserUpdateDto;
import com.yumeinaruu.iis.service.MarksService;
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
@RequestMapping("/marks")
@Tag(name = "Work with marks", description = "Methods to work with marks")
@SecurityRequirement(name = "Bearer Authentication")
public class MarksController {
    private final MarksService marksService;

    @Autowired
    public MarksController(MarksService marksService) {
        this.marksService = marksService;
    }

    @Operation(summary = "Gives info about all marks")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Marks info has returned successfully"),
            @ApiResponse(responseCode = "404", description = "Marks not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<List<Marks>> getAllMarks() {
        List<Marks> marks = marksService.getAllMarks();
        if (marks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(marks, HttpStatus.OK);
    }

    @Operation(summary = "Gives info about mark by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mark info has returned successfully"),
            @ApiResponse(responseCode = "404", description = "Mark not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<Marks> getMarksById(@PathVariable Long id) {
        Optional<Marks> marks = marksService.getMarkById(id);
        if (marks.isPresent()) {
            return new ResponseEntity<>(marks.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Gives info about marks sorted ascending")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Marks info has returned successfully"),
            @ApiResponse(responseCode = "404", description = "Marks not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @GetMapping("/ascending")
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<List<Marks>> getMarksAscending() {
        List<Marks> marks = marksService.getMarksAscending();
        if (marks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(marks, HttpStatus.OK);
    }

    @Operation(summary = "Gives info about marks sorted descending")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Marks info has returned successfully"),
            @ApiResponse(responseCode = "404", description = "Marks not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @GetMapping("/descending")
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<List<Marks>> getMarksDescending() {
        List<Marks> marks = marksService.getMarksDescending();
        if (marks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(marks, HttpStatus.OK);
    }

    @Operation(summary = "Creates mark")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Mark was created successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> createMarks(@RequestBody @Valid MarksCreateDto marksCreateDto,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(marksService.createMark(marksCreateDto) ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Updates mark overall")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Mark was updated successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PutMapping
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> updateMarks(@RequestBody @Valid MarksUpdateDto marksUpdateDto,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(marksService.updateMark(marksUpdateDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Updates mark(only mark field)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Mark was updated successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PutMapping("/mark")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> updateMarksMark(@RequestBody @Valid MarksMarkUpdateDto marksMarkUpdateDto,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(marksService.updateMarksMark(marksMarkUpdateDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Updates mark's subject")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Mark was updated successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PutMapping("/subject")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> updateMarksSubject(@RequestBody @Valid MarksSubjectUpdateDto marksSubjectUpdateDto,
                                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(marksService.updateMarksSubject(marksSubjectUpdateDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Updates user who has that mark")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Mark was updated successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PutMapping("/user")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> updateMarksUser(@RequestBody @Valid MarksUserUpdateDto marksUserUpdateDto,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(marksService.updateMarksUser(marksUserUpdateDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Deletes mark")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Mark was deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> deleteMarkById(@PathVariable Long id) {
        return new ResponseEntity<>(marksService.deleteMarkById(id) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }
}
