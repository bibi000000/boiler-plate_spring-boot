package com.example.demo.api.v1.service.impl;

import com.example.demo.api.entity.Member;
import com.example.demo.api.v1.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByEmail(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }

    //해당하는 User의 데이터가 존재한다면 userDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(Member member) {
        return new User(member.getUsername(), member.getPassword(), member.getAuthorities());
    }
}
