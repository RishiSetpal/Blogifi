package com.example.Blogifi.controllers;

import com.example.Blogifi.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmailController {
    @Autowired
    EmailService emailService;

    @GetMapping("/email/sent")
    public ResponseEntity<String> sentEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String body
    ){
        emailService.sendEmail(to, subject, body);
        return ResponseEntity.ok("Email sent Successfully to"+to);
    }
    @GetMapping("/email/send-promotion")
    public ResponseEntity<String> senfPromotional(){
        emailService.sendPromotionalEmails();
        return ResponseEntity.ok("Promotional Email has been sent");
    }
}
