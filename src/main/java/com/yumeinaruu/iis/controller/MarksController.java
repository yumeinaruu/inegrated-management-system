package com.yumeinaruu.iis.controller;

import com.yumeinaruu.iis.exception.custom_exception.CustomValidationException;
import com.yumeinaruu.iis.model.Marks;
import com.yumeinaruu.iis.model.dto.marks.MarksCreateDto;
import com.yumeinaruu.iis.model.dto.marks.MarksMarkUpdateDto;
import com.yumeinaruu.iis.model.dto.marks.MarksSubjectUpdateDto;
import com.yumeinaruu.iis.model.dto.marks.MarksUpdateDto;
import com.yumeinaruu.iis.model.dto.marks.MarksUserUpdateDto;
import com.yumeinaruu.iis.service.MarksService;
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
public class MarksController {
    private final MarksService marksService;

    @Autowired
    public MarksController(MarksService marksService) {
        this.marksService = marksService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<List<Marks>> getAllMarks() {
        List<Marks> marks = marksService.getAllMarks();
        if (marks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(marks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<Marks> getMarksById(@PathVariable Long id) {
        Optional<Marks> marks = marksService.getMarkById(id);
        if (marks.isPresent()) {
            return new ResponseEntity<>(marks.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/ascending")
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<List<Marks>> getMarksAscending() {
        List<Marks> marks = marksService.getMarksAscending();
        if (marks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(marks, HttpStatus.OK);
    }

    @GetMapping("/descending")
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<List<Marks>> getMarksDescending() {
        List<Marks> marks = marksService.getMarksDescending();
        if (marks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(marks, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> createMarks(@RequestBody @Valid MarksCreateDto marksCreateDto,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(marksService.createMark(marksCreateDto) ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> updateMarks(@RequestBody @Valid MarksUpdateDto marksUpdateDto,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(marksService.updateMark(marksUpdateDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @PutMapping("/mark")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> updateMarksMark(@RequestBody @Valid MarksMarkUpdateDto marksMarkUpdateDto,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(marksService.updateMarksMark(marksMarkUpdateDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @PutMapping("/subject")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> updateMarksSubject(@RequestBody @Valid MarksSubjectUpdateDto marksSubjectUpdateDto,
                                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(marksService.updateMarksSubject(marksSubjectUpdateDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @PutMapping("/user")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> updateMarksUser(@RequestBody @Valid MarksUserUpdateDto marksUserUpdateDto,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(marksService.updateMarksUser(marksUserUpdateDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> deleteMarkById(@PathVariable Long id) {
        return new ResponseEntity<>(marksService.deleteMarkById(id) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }
}
