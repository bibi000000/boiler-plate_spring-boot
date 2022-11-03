package com.example.demo.api.v1.service;

import com.example.demo.api.v1.dto.request.MemberRequestDto;
import org.springframework.http.ResponseEntity;

public interface MemberService {

    public ResponseEntity<?> signUp(MemberRequestDto.SignUp signUp);
    public ResponseEntity<?> login(MemberRequestDto.Login login);
    public ResponseEntity<?> reissue(MemberRequestDto.Reissue reissue);
    public ResponseEntity<?> logout(MemberRequestDto.Logout logout);
    public ResponseEntity<?> authority();



}
