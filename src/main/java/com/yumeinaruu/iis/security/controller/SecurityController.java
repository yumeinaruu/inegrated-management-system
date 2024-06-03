package com.yumeinaruu.iis.security.controller;

import com.yumeinaruu.iis.exception.custom_exception.CustomValidationException;
import com.yumeinaruu.iis.security.model.dto.AuthRequestDto;
import com.yumeinaruu.iis.security.model.dto.AuthResponseDto;
import com.yumeinaruu.iis.security.model.dto.GiveRoleDto;
import com.yumeinaruu.iis.security.model.dto.NotStudentRegistrationDto;
import com.yumeinaruu.iis.security.model.dto.RegistrationDto;
import com.yumeinaruu.iis.security.service.SecurityService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Tag(name = "Work with security", description = "Methods to work with security")
@RequestMapping("/security")
@SecurityRequirement(name = "Bearer Authentication")
public class SecurityController {
    private final SecurityService securityService;

    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Operation(summary = "Register a new student(using email)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Student was registered successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PostMapping("/registration")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid RegistrationDto registrationDto,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        securityService.registration(registrationDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Register a new teacher(using email)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Teacher was registered successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PostMapping("/registration/teacher")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> registrationForTeachers(@RequestBody @Valid NotStudentRegistrationDto registrationDto,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        securityService.registrationForTeachers(registrationDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Register a new admin(using email)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Admin was registered successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PostMapping("/registration/admin")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> registrationForAdmin(@RequestBody @Valid NotStudentRegistrationDto registrationDto,
                                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        securityService.registrationForAdmin(registrationDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Method that generates JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "JWT was created"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "401", description = "You are unauthorized"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PostMapping("/token")
    public ResponseEntity<AuthResponseDto> generateToken(@RequestBody @Valid AuthRequestDto authRequest,
                                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        Optional<String> token = securityService.generateToken(authRequest);
        if (token.isPresent()) {
            return new ResponseEntity<>(new AuthResponseDto(token.get()), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Operation(summary = "Promote user to admin")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User was promoted successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PutMapping("/give-admin")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> giveAdmin(@RequestBody @Valid GiveRoleDto giveRoleDto,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(securityService.giveAdmin(giveRoleDto) ? HttpStatus.NO_CONTENT : HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Promote user to teacher")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User was promoted successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PutMapping("/give-teacher")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> giveTeacher(@RequestBody @Valid GiveRoleDto giveRoleDto,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(securityService.giveTeacher(giveRoleDto) ? HttpStatus.NO_CONTENT : HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Downgrade admin")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Admin was downgraded successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PutMapping("/downgrade-admin")
    @PreAuthorize("hasAnyRole('SUPERADMIN')")
    public ResponseEntity<HttpStatus> downgradeAdmin(@RequestBody @Valid GiveRoleDto giveRoleDto,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(securityService.downgradeAdmin(giveRoleDto) ? HttpStatus.NO_CONTENT : HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Downgrade teacher")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Teacher was downgraded successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PutMapping("/downgrade-teacher")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> downgradeTeacher(@RequestBody @Valid GiveRoleDto giveRoleDto,
                                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(securityService.downgradeTeacher(giveRoleDto) ? HttpStatus.NO_CONTENT : HttpStatus.BAD_REQUEST);
    }
}
