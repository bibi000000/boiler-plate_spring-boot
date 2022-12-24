package com.wyaa.demo3.api.v1.controller;

import com.wyaa.demo3.api.v1.dto.SignRequest;
import com.wyaa.demo3.api.v1.dto.SignResponse;
import com.wyaa.demo3.api.v1.dto.TokenDTO;
import com.wyaa.demo3.api.v1.repository.MemberRepository;
import com.wyaa.demo3.api.v1.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SignController {
    private final MemberRepository memberRepository;
    private final SignService signService;

    @PostMapping(value = "/login")
    public ResponseEntity<SignResponse> signIn(@RequestBody SignRequest signRequest) throws Exception {
        return new ResponseEntity<>(signService.login(signRequest), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Boolean> signUp(@RequestBody SignRequest signRequest) throws Exception {
        return new ResponseEntity<>(signService.register(signRequest), HttpStatus.OK);
    }

    @GetMapping(value = "/user/get")
    public ResponseEntity<SignResponse> getUser(@RequestParam String account) throws Exception {
        return new ResponseEntity<>(signService.getMember(account), HttpStatus.OK);
    }

    @GetMapping(value = "/admin/get")
    public ResponseEntity<SignResponse> getAdmin(@RequestParam String account) throws Exception {
        return new ResponseEntity<>(signService.getMember(account), HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity<TokenDTO> refresh(@RequestBody TokenDTO tokenDTO) throws Exception {
        return new ResponseEntity<>(signService.refreshAccessToken(tokenDTO), HttpStatus.OK);
    }
}
