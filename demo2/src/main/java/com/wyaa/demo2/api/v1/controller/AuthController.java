package com.wyaa.demo2.api.v1.controller;

import com.wyaa.demo2.api.v1.dto.request.UserAccountLoginPostReq;
import com.wyaa.demo2.api.v1.dto.response.UserAccountLoginPostRes;
import com.wyaa.demo2.api.v1.service.AuthService;
import com.wyaa.demo2.api.v1.service.UserAccountService;
import com.wyaa.demo2.common.util.JwtTokenUtil;
import com.wyaa.demo2.entity.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserAccountLoginPostRes> login(@RequestBody UserAccountLoginPostReq userAccountLoginPostReq) {
        String accessToken = authService.login(userAccountLoginPostReq);
        if (accessToken!=null) {
            return ResponseEntity.ok(UserAccountLoginPostRes.of(200, "success", accessToken));
        }
        return ResponseEntity.status(401).body(UserAccountLoginPostRes.of(401, "Invalid Password", null));
    }

}
