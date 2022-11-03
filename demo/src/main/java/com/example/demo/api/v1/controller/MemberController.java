package com.example.demo.api.v1.controller;

import com.example.demo.api.jwt.JwtTokenProvider;
import com.example.demo.api.lib.Helper;
import com.example.demo.api.v1.dto.Response;
import com.example.demo.api.v1.dto.request.MemberRequestDto;
import com.example.demo.api.v1.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class MemberController {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final Response response;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Validated MemberRequestDto.SignUp signUp, Errors errors) {
        //validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return memberService.signUp(signUp);
    }
}
