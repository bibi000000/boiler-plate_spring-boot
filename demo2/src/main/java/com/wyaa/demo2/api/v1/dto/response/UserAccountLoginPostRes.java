package com.wyaa.demo2.api.v1.dto.response;

import com.wyaa.demo2.common.model.response.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAccountLoginPostRes extends BaseResponseBody {
    String accessToken;

    public static UserAccountLoginPostRes of (Integer statusCode, String message, String accessToken) {
        UserAccountLoginPostRes res = new UserAccountLoginPostRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setAccessToken(accessToken);
        return res;
    }
}
