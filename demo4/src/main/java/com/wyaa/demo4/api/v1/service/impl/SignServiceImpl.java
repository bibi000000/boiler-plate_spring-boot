package com.wyaa.demo4.api.v1.service.impl;

import com.wyaa.demo4.api.v1.dto.SignRequest;
import com.wyaa.demo4.api.v1.dto.SignResponse;
import com.wyaa.demo4.api.v1.dto.TokenDTO;
import com.wyaa.demo4.api.v1.repository.MemberRepository;
import com.wyaa.demo4.api.v1.repository.TokenRepository;
import com.wyaa.demo4.api.v1.service.SignService;
import com.wyaa.demo4.entity.Authority;
import com.wyaa.demo4.entity.Member;
import com.wyaa.demo4.security.JwtProvider;
import com.wyaa.demo4.security.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    private final TokenRepository tokenRepository;

    @Override
    public SignResponse login(SignRequest signRequest) throws Exception{
        Member member = memberRepository.findByAccount(signRequest.getAccount())
                .orElseThrow(() -> new BadCredentialsException("잘못된 계정정보입니다. - 계정 없음"));

        member.setRefreshToken(createRefreshToken(member));
        memberRepository.save(member);

        if (!passwordEncoder.matches(signRequest.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("잘못된 계정정보입니다. - 비밀번호 매칭");
        }

        return SignResponse.builder()
                .id(member.getId())
                .account(member.getAccount())
                .name(member.getName())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .roles(member.getRoles())
                .token(TokenDTO.builder()
                        .accessToken(jwtProvider.createToken(member.getAccount(), member.getRoles()))
                        .refreshToken(member.getRefreshToken())
                        .build()
                )
                .build();
    }

    @Override
    public boolean register(SignRequest signRequest) throws Exception{
        try {
            Member member = Member.builder()
                    .account(signRequest.getAccount())
                    .password(passwordEncoder.encode(signRequest.getPassword()))
                    .name(signRequest.getName())
                    .nickname(signRequest.getNickname())
                    .email(signRequest.getEmail())
                    .build();

            member.setRoles(Collections.singletonList(Authority.builder().name("ROLE_USER").build()));
//            member.setRoles(Collections.singletonList(Authority.builder().name("USER").build()));
            memberRepository.save(member);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("잘못된 요청입ㄴ디다.");
        }
        return true;
    }

    @Override
    public SignResponse getMember(String account) throws Exception{
        Member member = memberRepository.findByAccount(account)
                .orElseThrow(() -> new Exception("계정을 찾을 수 없습니다."));
        return new SignResponse(member, TokenDTO.builder()
                .accessToken(jwtProvider.createToken(member.getAccount(), member.getRoles()))
                .refreshToken(member.getRefreshToken())
                .build());
    }

    // ======= Refresh Token =======

    /**
     * Refresh 토큰을 생성한다.
     * Redis 내부에는
     *      refreshToken:memberId : tokenValue
     *      형태로 저장한다.
     */
    @Override
    public String createRefreshToken(Member member) {
        Token token = tokenRepository.save(
                Token.builder()
                        .id(member.getId())
                        .refreshToken(UUID.randomUUID().toString())
                        .expiration(120)
                        .build()
        );
        return token.getRefreshToken();
    }

    @Override
    public Token validRefreshToken(Member member, String refreshToken) throws Exception {
        Token token = tokenRepository.findById(member.getId())
                .orElseThrow(() -> new Exception("만료된 계정입니다. 로그인을 다시 시도하세요."));
        // 해당 유저의 Refresh 토큰 만료: Redis에 해당 유저의 토큰이 존재하지 않음
        if (token.getRefreshToken()==null) {
            return null;
        } else {
            // 리프레시 토큰 만료일자가 얼마 남지 않았을 때 만료 시간 연장 ... ?
            if (token.getExpiration()<10) {
                token.setExpiration(1000);
                tokenRepository.save(token);
            }
            // 토큰이 같은지 비교
            if (!token.getRefreshToken().equals(refreshToken)) {
                return null;
            } else {
                return token;
            }
        }
    }

    @Override
    public TokenDTO refreshAccessToken(TokenDTO tokenDTO) throws Exception {
        String account = jwtProvider.getAccount(tokenDTO.getAccessToken());
        Member member = memberRepository.findByAccount(account)
                .orElseThrow(() -> new BadCredentialsException("잘못된 계정정보입니다."));
        Token refreshToken = validRefreshToken(member, tokenDTO.getRefreshToken());

        if (refreshToken!=null) {
            return TokenDTO.builder()
                    .accessToken(jwtProvider.createToken(account, member.getRoles()))
                    .refreshToken(refreshToken.getRefreshToken())
                    .build();
        } else {
            throw new Exception("로그인을 해주세요.");
        }
    }
}
