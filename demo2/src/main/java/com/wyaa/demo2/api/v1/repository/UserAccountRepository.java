package com.wyaa.demo2.api.v1.repository;

import com.wyaa.demo2.api.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
    Optional<UserAccount> findByUserAccountEmail(String userAccountEmail);
    Optional<UserAccount> findByUserAccountNickname(String userAccountNickname);
}
