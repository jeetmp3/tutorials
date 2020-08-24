package com.demo.security.config;

import org.springframework.security.core.Authentication;

public interface ITokenStore {

    String generateToken( Authentication authentication ) throws Exception;

    Authentication getAuth( String token ) throws Exception;
}
