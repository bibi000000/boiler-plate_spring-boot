package com.example.demo.api.v1.service;

import com.example.demo.api.v1.dto.request.MemberRequestDto;
import org.springframework.http.ResponseEntity;

public interface MemberService {
    public ResponseEntity<?> login(MemberRequestDto.Login login);


//    public ResponseEntity<?> login(UserRequestDto.Login login) {
//
//        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
//        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
//        UsernamePasswordAuthenticationToken authenticationToken = login.toAuthentication();
//
//        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
//        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//
//        // 3. 인증 정보를 기반으로 JWT 토큰 생성
//        UserResponseDto.TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
//
//        // TODO:: RefreshToken Redis 저장
//
//        return response.success(tokenInfo, "로그인에 성공했습니다.", HttpStatus.OK);
//    }
}
