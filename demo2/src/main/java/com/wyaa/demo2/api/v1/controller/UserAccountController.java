package com.wyaa.demo2.api.v1.controller;

import com.wyaa.demo2.api.v1.dto.request.UserAccountRegisterPostReq;
import com.wyaa.demo2.api.v1.dto.response.UserAccountRes;
import com.wyaa.demo2.api.v1.service.UserAccountService;
import com.wyaa.demo2.common.auth.UserDetailsImpl;
import com.wyaa.demo2.common.model.response.BaseResponseBody;
import com.wyaa.demo2.entity.UserAccount;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/user-account")
@RequiredArgsConstructor
@Api(value = "User account API", tags = {"UserAccount"})
public class UserAccountController {
    final UserAccountService userAccountService;

    @PostMapping
    public ResponseEntity<? extends BaseResponseBody> register(
            @RequestBody UserAccountRegisterPostReq userAccountRegisterPostReq) {
        UserAccount userAccount = userAccountService.createUserAccount(userAccountRegisterPostReq);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success"));
    }

    @GetMapping
    public ResponseEntity<UserAccountRes> detail(@ApiIgnore Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getDetails();
        String userAccountEmail = userDetails.getUsername();
        UserAccount userAccount = userAccountService.getUserAccountByUserAccountEmail(userAccountEmail);
        return ResponseEntity.status(200).body(UserAccountRes.of(userAccount));
    }
}
