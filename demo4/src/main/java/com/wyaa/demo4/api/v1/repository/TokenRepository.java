package com.wyaa.demo4.api.v1.repository;

import com.wyaa.demo4.security.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, Long> {

}
