package com.kartheek.springsecurity.profile.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class ProfileController {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public  ResponseEntity<String> helloAdmin(){
        return ResponseEntity.ok("Hello Admin");
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/user")
    public  ResponseEntity<String> helloUser(){
        return ResponseEntity.ok("Hello User");
    }

}
