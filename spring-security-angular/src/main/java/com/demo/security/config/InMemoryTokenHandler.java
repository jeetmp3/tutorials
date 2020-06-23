package com.demo.security.config;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Component
public class InMemoryTokenHandler {

    private ConcurrentMap< String, Authentication > tokenHandler;

    public InMemoryTokenHandler() {
        this.tokenHandler = new ConcurrentHashMap<>();
    }

    public String saveAndGetToken( Authentication auth ) {

        String tmpToken = generateJwt( ( DefaultOAuth2User ) auth.getPrincipal() );
        tokenHandler.put( tmpToken, auth );
        return tmpToken;
    }

    public Authentication getAuthObject( String token ) {

        return tokenHandler.get( token );
    }

    private String generateJwt( DefaultOAuth2User user ) {
        Map< String, Object > attribs = new HashMap<>( user.getAttributes() );
        Set< String > authorities = user.getAuthorities().stream()
                .map( GrantedAuthority::getAuthority )
                .collect( Collectors.toSet() );
        attribs.put( "authorities", authorities );
        JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
        attribs.forEach( builder::claim );

        JWT jwt = new PlainJWT( builder.build() );
        return jwt.serialize();
    }
}
