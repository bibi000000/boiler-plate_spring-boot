package com.wyaa.demo2.api.v1.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserAccountRegisterPostReq")
public class UserAccountRegisterPostReq {
    @ApiModelProperty(name="User account email", example="email")
    String userAccountEmail;
    @ApiModelProperty(name = "User account password", example = "password")
    String userAccountPassword;
    @ApiModelProperty(name = "User accuont nickname", example = "nickname")
    String userAccountNickname;
}
