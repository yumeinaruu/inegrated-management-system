package com.yumeinaruu.iis.security.controller;

import com.yumeinaruu.iis.exception.custom_exception.CustomValidationException;
import com.yumeinaruu.iis.security.model.dto.AuthRequestDto;
import com.yumeinaruu.iis.security.model.dto.AuthResponseDto;
import com.yumeinaruu.iis.security.model.dto.GiveRoleDto;
import com.yumeinaruu.iis.security.model.dto.RegistrationDto;
import com.yumeinaruu.iis.security.service.SecurityService;
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
@RequestMapping("/security")
public class SecurityController {
    private final SecurityService securityService;

    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid RegistrationDto registrationDto,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        securityService.registration(registrationDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

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

    @PutMapping("/give-admin")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> giveAdmin(@RequestBody @Valid GiveRoleDto giveRoleDto,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(securityService.giveAdmin(giveRoleDto) ? HttpStatus.NO_CONTENT : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/give-teacher")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> giveTeacher(@RequestBody @Valid GiveRoleDto giveRoleDto,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(securityService.giveTeacher(giveRoleDto) ? HttpStatus.NO_CONTENT : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/downgrade-admin")
    @PreAuthorize("hasAnyRole('SUPERADMIN')")
    public ResponseEntity<HttpStatus> downgradeAdmin(@RequestBody @Valid GiveRoleDto giveRoleDto,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(securityService.downgradeAdmin(giveRoleDto) ? HttpStatus.NO_CONTENT : HttpStatus.BAD_REQUEST);
    }

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
