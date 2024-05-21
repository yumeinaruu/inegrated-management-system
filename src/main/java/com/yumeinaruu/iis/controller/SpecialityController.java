package com.yumeinaruu.iis.controller;

import com.yumeinaruu.iis.exception.custom_exception.CustomValidationException;
import com.yumeinaruu.iis.model.Speciality;
import com.yumeinaruu.iis.model.dto.speciality.SpecialityCreateDto;
import com.yumeinaruu.iis.model.dto.speciality.SpecialityUpdateDto;
import com.yumeinaruu.iis.model.dto.speciality.SpecialityUpdateFacultyDto;
import com.yumeinaruu.iis.model.dto.speciality.SpecialityUpdateNameDto;
import com.yumeinaruu.iis.service.SpecialityService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/speciality")
@SecurityRequirement(name = "Bearer Authentication")
public class SpecialityController {
    private final SpecialityService specialityService;

    @Autowired
    public SpecialityController(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @GetMapping
    public ResponseEntity<List<Speciality>> getAllSpecialities() {
        List<Speciality> specialities = specialityService.getAllSpecialities();
        if (specialities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(specialities, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Speciality> getSpecialityById(@PathVariable Long id) {
        Optional<Speciality> speciality = specialityService.getSpecialityById(id);
        if (speciality.isPresent()) {
            return new ResponseEntity<>(speciality.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Speciality> getSpecialityByName(@PathVariable String name) {
        Optional<Speciality> speciality = specialityService.getSpecialityByName(name);
        if (speciality.isPresent()) {
            return new ResponseEntity<>(speciality.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/name-sorted")
    public ResponseEntity<List<Speciality>> getSpecialitiesSortedByName() {
        List<Speciality> specialities = specialityService.getSpecialitiesSortedByName();
        if (specialities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(specialities, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> createSpeciality(@RequestBody @Valid SpecialityCreateDto specialityCreateDto,
                                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(specialityService.createSpeciality(specialityCreateDto) ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> updateSpeciality(@RequestBody @Valid SpecialityUpdateDto specialityUpdateDto,
                                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(specialityService.updateSpeciality(specialityUpdateDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @PutMapping("/name")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> updateSpecialityName(@RequestBody @Valid SpecialityUpdateNameDto specialityUpdateNameDto,
                                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(specialityService.updateSpecialityName(specialityUpdateNameDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @PutMapping("/faculty")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> updateSpecialityFaculty(@RequestBody @Valid SpecialityUpdateFacultyDto specialityUpdateFacultyDto,
                                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(specialityService.updateSpecialityFaculty(specialityUpdateFacultyDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> deleteSpecialityById(@PathVariable Long id) {
        return new ResponseEntity<>(specialityService.deleteSpeciality(id) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }
}
