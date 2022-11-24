package com.wyaa.demo2.api.v1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user-account")
public class UserAccountController {
    @GetMapping("/login")
    public ResponseEntity login() {
    }

}
