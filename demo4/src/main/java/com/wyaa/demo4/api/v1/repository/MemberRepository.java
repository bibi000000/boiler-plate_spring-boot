package com.wyaa.demo4.api.v1.repository;

import com.wyaa.demo4.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByAccount(String account);
}
