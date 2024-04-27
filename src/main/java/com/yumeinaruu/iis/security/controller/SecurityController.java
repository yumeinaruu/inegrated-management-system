package com.yumeinaruu.iis.security.controller;

import com.yumeinaruu.iis.security.model.dto.AuthRequestDto;
import com.yumeinaruu.iis.security.model.dto.AuthResponseDto;
import com.yumeinaruu.iis.security.model.dto.RegistrationDto;
import com.yumeinaruu.iis.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<HttpStatus> registration(@RequestBody RegistrationDto registrationDto) {
        securityService.registration(registrationDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/token")
    public ResponseEntity<AuthResponseDto> generateToken(@RequestBody AuthRequestDto authRequest) {
        Optional<String> token = securityService.generateToken(authRequest);
        if (token.isPresent()) {
            return new ResponseEntity<>(new AuthResponseDto(token.get()), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
