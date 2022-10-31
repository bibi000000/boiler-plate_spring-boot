package com.example.demo.api.v1.service.impl;

import com.example.demo.api.v1.dto.request.MemberRequestDto;
import com.example.demo.api.v1.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    @Override
    public ResponseEntity<?> login(MemberRequestDto.Login login) {
        return null;
    }
}
