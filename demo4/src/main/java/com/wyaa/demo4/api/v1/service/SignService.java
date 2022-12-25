package com.wyaa.demo4.api.v1.service;

import com.wyaa.demo4.api.v1.dto.SignRequest;
import com.wyaa.demo4.api.v1.dto.SignResponse;
import com.wyaa.demo4.api.v1.dto.TokenDTO;
import com.wyaa.demo4.entity.Member;
import com.wyaa.demo4.security.Token;

public interface SignService {
    SignResponse login(SignRequest signRequest) throws Exception;
    boolean register(SignRequest signRequest) throws Exception;
    SignResponse getMember(String account) throws Exception;
    String createRefreshToken(Member member);
    Token validRefreshToken(Member member, String refreshToken) throws Exception;
    TokenDTO refreshAccessToken(TokenDTO tokenDTO) throws Exception;
}
