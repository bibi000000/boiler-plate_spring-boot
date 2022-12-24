package com.wyaa.demo3.api.v1.dto;

import com.wyaa.demo3.entity.Authority;
import com.wyaa.demo3.entity.Member;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignResponse {
    private Long id;
    private String account;
    private String nickname;
    private String name;
    private String email;
    private List<Authority> roles = new ArrayList<>();
    private TokenDTO token;

    public SignResponse(Member member, TokenDTO token) {
        this.id = member.getId();
        this.account = member.getAccount();
        this.nickname = member.getNickname();
        this.name = member.getName();
        this.email = member.getEmail();
        this.roles = member.getRoles();

        this.token = token;
    }
}
