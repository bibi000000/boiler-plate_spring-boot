package com.wyaa.demo4.security;

import com.wyaa.demo4.api.v1.repository.MemberRepository;
import com.wyaa.demo4.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByAccount(username)
                .orElseThrow(
                        ()->new UsernameNotFoundException("[.security.JpaUserDetailsService] Invalid authentication !")
                );
        return new CustomUserDetails(member);
    }
}
