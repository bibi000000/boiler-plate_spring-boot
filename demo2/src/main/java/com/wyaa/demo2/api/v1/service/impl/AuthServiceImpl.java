package com.wyaa.demo2.api.v1.service.impl;

import com.wyaa.demo2.api.v1.dto.request.UserAccountLoginPostReq;
import com.wyaa.demo2.api.v1.repository.UserAccountRepository;
import com.wyaa.demo2.api.v1.service.AuthService;
import com.wyaa.demo2.common.util.JwtTokenUtil;
import com.wyaa.demo2.common.util.RedisUtil;
import com.wyaa.demo2.entity.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final RedisUtil redisUtil;

    @Value("${jwt.refresh-token-expiration}")
    private Integer refreshTokenExpiration;
    @Override
    public String login(UserAccountLoginPostReq userAccountLoginPostReq) {
        UserAccount userAccount = userAccountRepository.findByUserAccountEmail(userAccountLoginPostReq.getUserAccountEmail()).get();
        if (passwordEncoder.matches(userAccountLoginPostReq.getUserAccountPassword(), userAccount.getUserAccountPassword())) {
            String accessToken = jwtTokenUtil.createAccessToken(userAccount.getUserAccountSeq());
            return accessToken;
        }
        return null;
    }

    @Transactional
    @Override
    public void logout(String accessToken, String refreshToken) {
        if (!jwtTokenUtil.validateToken(accessToken)) {
            throw new U
        }
        Authentication authentication = jwtTokenUtil.getAuthentication(accessToken);
        Long expiration = jwtTokenUtil.getTokenExpirationAsLong(accessToken);

    }
}
