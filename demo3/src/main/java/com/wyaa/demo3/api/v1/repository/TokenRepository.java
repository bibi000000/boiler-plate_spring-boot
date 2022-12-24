package com.wyaa.demo3.api.v1.repository;

import com.wyaa.demo3.security.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, Long> {

}
