package com.wyaa.demo2.common.auth;

import com.wyaa.demo2.api.v1.repository.UserAccountRepository;
import com.wyaa.demo2.api.v1.service.UserAccountService;
import com.wyaa.demo2.entity.UserAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    final UserAccountService userAccountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountService.getUserAccountByUserAccountEmail(username);
        if (userAccount!=null) {
            UserDetailsImpl userDetails = new UserDetailsImpl(userAccount);
            return userDetails;
        }
        return null;
    }
}
