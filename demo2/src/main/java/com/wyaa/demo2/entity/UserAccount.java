package com.wyaa.demo2.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccount{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_account_seq", nullable = false, columnDefinition = "INT UNSIGNED UNIQUE")
    private int userAccountSeq;

    @Column(name = "user_account_email", nullable = false, columnDefinition = "VARCHAR(100) UNIQUE")
    private String userAccountEmail;

    @Column(name = "user_account_password", nullable = false, columnDefinition = "VARCHAR(100) UNIQUE")
    private String userAccountPassword;

    @Column(name = "user_account_nickname", nullable = false, columnDefinition = "VARCHAR(20) UNIQUE")
    private String userAccountNickname;

//    @Column(name = "user_account_role", nullable = false, columnDefinition = "VARCHAR(3)")
    @Column(name = "user_account_role", nullable = true, columnDefinition = "VARCHAR(3)")
    private String userAccountRole;
}
