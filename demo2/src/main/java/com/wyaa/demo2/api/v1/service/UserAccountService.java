package com.wyaa.demo2.api.v1.service;

import com.wyaa.demo2.api.v1.dto.request.UserAccountLoginPostReq;
import com.wyaa.demo2.api.v1.dto.request.UserAccountRegisterPostReq;
import com.wyaa.demo2.entity.UserAccount;

public interface UserAccountService {
    UserAccount createUserAccount(UserAccountRegisterPostReq userAccountRegisterPostReq);
    UserAccount getUserAccountByUserAccountEmail(String userAccountEmail);
}
