package com.wyaa.demo2.api.v1.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAccountLoginPostReq {
    String userAccountEmail;
    String userAccountPassword;
}
