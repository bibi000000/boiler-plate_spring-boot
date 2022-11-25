package com.wyaa.demo2.api.v1.service.impl;

import com.wyaa.demo2.api.v1.dto.request.UserAccountLoginPostReq;
import com.wyaa.demo2.api.v1.dto.request.UserAccountRegisterPostReq;
import com.wyaa.demo2.api.v1.dto.response.UserAccountLoginPostRes;
import com.wyaa.demo2.api.v1.repository.UserAccountRepository;
import com.wyaa.demo2.api.v1.service.UserAccountService;
import com.wyaa.demo2.common.util.JwtTokenUtil;
import com.wyaa.demo2.entity.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserAccount createUserAccount(UserAccountRegisterPostReq userAccountRegisterPostReq) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUserAccountEmail(userAccountRegisterPostReq.getUserAccountEmail());
        userAccount.setUserAccountPassword(passwordEncoder.encode(userAccountRegisterPostReq.getUserAccountPassword()));
        userAccount.setUserAccountNickname(userAccountRegisterPostReq.getUserAccountNickname());
        return userAccountRepository.save(userAccount);
    }

    @Override
    public UserAccount getUserAccountByUserAccountEmail(String userAccountEmail) {
        UserAccount userAccount = userAccountRepository.findByUserAccountEmail(userAccountEmail).get();
        return userAccount;
    }
}
