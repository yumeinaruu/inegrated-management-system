package com.yumeinaruu.iis.controller;

import com.yumeinaruu.iis.service.EmailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/mail")
@SecurityRequirement(name = "Bearer Authentication")
public class EmailSenderController {
    private final EmailService emailService;

    @Autowired
    public EmailSenderController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public String sendEmail(@RequestParam(value = "file", required = false) MultipartFile[] file,
                          String to, String[] cc, String subject, String body) {
        return emailService.sendEmail(file, to, cc, subject, body);
    }
}
