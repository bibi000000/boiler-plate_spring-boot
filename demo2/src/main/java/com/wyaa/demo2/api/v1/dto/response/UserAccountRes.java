package com.wyaa.demo2.api.v1.dto.response;

import com.wyaa.demo2.entity.UserAccount;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserAccuontRes")
public class UserAccountRes {
    String userAccountEmail;
    String userAccountNickname;
    public static UserAccountRes of (UserAccount userAccount) {
        UserAccountRes res = new UserAccountRes();
        res.setUserAccountEmail(userAccount.getUserAccountEmail());
        res.setUserAccountNickname(userAccount.getUserAccountNickname());
        return res;
    }
}
