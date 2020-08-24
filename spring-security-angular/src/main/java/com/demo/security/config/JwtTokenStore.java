package com.demo.security.config;

import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.gen.ECKeyGenerator;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import java.security.interfaces.ECPrivateKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtTokenStore implements ITokenStore {

    private final String tokenSignKey = "c5d4d70419bd4909a1e502812c6e1f2b";

    private final String REG_ID = "clientRegistrationId";
    private final String NAMED_KEY = "namedAttributeKey";
    private final String AUTHORITIES = "authorities";
    private final String ATTRIBUTES = "attributes";

    public Authentication getAuth( String jwt ) throws Exception {
        SignedJWT signedJWT = SignedJWT.parse( jwt );
        validateJwt( signedJWT );

        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

        String clientRegistrationId = (String ) claimsSet.getClaim( REG_ID );
        String namedAttributeKey = (String) claimsSet.getClaim( NAMED_KEY );
        Map<String, Object> attributes = (Map<String, Object>)claimsSet.getClaim( ATTRIBUTES );
        Collection<? extends GrantedAuthority > authorities =( (List<String> ) claimsSet.getClaim( AUTHORITIES ))
                .stream().map( SimpleGrantedAuthority::new ).collect( Collectors.toSet());

        return new OAuth2AuthenticationToken(
                new DefaultOAuth2User( authorities, attributes, namedAttributeKey ),
                authorities,
                clientRegistrationId
        );
    }

    public String generateToken( Authentication authentication ) throws Exception {

        OAuth2AuthenticationToken token = ( OAuth2AuthenticationToken ) authentication;
        DefaultOAuth2User userDetails = ( DefaultOAuth2User ) token.getPrincipal();

        List<String> auths = userDetails.getAuthorities()
                .stream()
                .map( GrantedAuthority::getAuthority )
                .collect( Collectors.toList());
        //add tenant
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(  userDetails.getAttribute("id").toString())
                .expirationTime( getDate( 5, ChronoUnit.HOURS ) )
                .claim( NAMED_KEY, "name" )
                .claim( ATTRIBUTES, userDetails.getAttributes() )
                .claim( AUTHORITIES, auths )
                .claim( REG_ID, token.getAuthorizedClientRegistrationId() )
                .build();


        ECKey key = new ECKeyGenerator( Curve.P_256 ).keyID( tokenSignKey ).generate();
        JWSHeader h = new JWSHeader.Builder( JWSAlgorithm.ES256 )
                .type( JOSEObjectType.JWT )
                .keyID( key.getKeyID() )
                .build();
        SignedJWT jwt = new SignedJWT( h, claimsSet );
        jwt.sign( new ECDSASigner( ( ECPrivateKey ) key.toPrivateKey() ) );
        return jwt.serialize();
    }

    private static Date getDate( long amount, TemporalUnit unit ) {
        return Date.from(
                LocalDateTime.now()
                .plus( amount, unit )
                .atZone( ZoneId.systemDefault() )
                .toInstant()
        );
    }

    private void validateJwt( JWT jwt ) throws Exception {
        if(jwt.getJWTClaimsSet().getExpirationTime().before( new Date() )){
            throw new RuntimeException("Token Expired!!");
        }

        // Add validation logic here..
    }
}
