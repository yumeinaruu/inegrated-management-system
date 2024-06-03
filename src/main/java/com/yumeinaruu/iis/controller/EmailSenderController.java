package com.yumeinaruu.iis.controller;

import com.yumeinaruu.iis.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Tag(name = "Work with email", description = "Methods to work with email")
@RequestMapping("/mail")
@SecurityRequirement(name = "Bearer Authentication")
public class EmailSenderController {
    private final EmailService emailService;

    @Autowired
    public EmailSenderController(EmailService emailService) {
        this.emailService = emailService;
    }

    @Operation(summary = "Sends email with attachment")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Email sent successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PostMapping("/send")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> sendEmail(@RequestParam(value = "file", required = false) MultipartFile[] file,
                                                String to, String[] cc, String subject, String body) {
        return new ResponseEntity<>(emailService.sendEmail(file, to, cc, subject, body) ? HttpStatus.OK : HttpStatus.CONFLICT);
    }
}
