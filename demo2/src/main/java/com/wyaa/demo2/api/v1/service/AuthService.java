package com.wyaa.demo2.api.v1.service;

import com.wyaa.demo2.api.v1.dto.request.UserAccountLoginPostReq;

public interface AuthService {
    String login(UserAccountLoginPostReq userAccountLoginPostReq);
    void logout(String accessToken, String refreshToken);
}
