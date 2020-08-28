package com.jsblogs.security.jwt.security;

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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.interfaces.ECPrivateKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;

@Component
public class JwtTokenStore {

    private final String tokenSignKey = "c5d4d70419bd4909a1e502812c6e1f2b";

    private final AppUserDetailsService userDetailsService;

    public JwtTokenStore( AppUserDetailsService userDetailsService ) {
        this.userDetailsService = userDetailsService;
    }

    public String generateToken( Authentication authentication ) throws Exception {

        UserDetails userDetails = ( UserDetails ) authentication.getPrincipal();

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(  userDetails.getUsername())
                .expirationTime( getDate( 5, ChronoUnit.HOURS ) )
                .build();

        // 6
        ECKey key = new ECKeyGenerator( Curve.P_256 ).keyID( tokenSignKey ).generate();
        JWSHeader h = new JWSHeader.Builder( JWSAlgorithm.ES256 )
                .type( JOSEObjectType.JWT )
                .keyID( key.getKeyID() )
                .build();
        SignedJWT jwt = new SignedJWT( h, claimsSet );
        // 7
        jwt.sign( new ECDSASigner( ( ECPrivateKey ) key.toPrivateKey() ) );
        return jwt.serialize();
    }

    // 8
    public Authentication getAuth( String jwt ) throws Exception {
        SignedJWT signedJWT = SignedJWT.parse( jwt );
        // 9
        validateJwt( signedJWT );

        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
        String email = claimsSet.getSubject();
        UserDetails details = userDetailsService.loadUserByUsername( email );

        // 11
        return new UsernamePasswordAuthenticationToken(
                details, details.getPassword(), details.getAuthorities()
        );
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
        // 12
        if(jwt.getJWTClaimsSet().getExpirationTime().before( new Date() )){
            throw new RuntimeException("Token Expired!!");
        }

        // Add validation logic here..
    }
}
