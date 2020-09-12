package com.jsblogs.azure.b2c;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http.authorizeRequests()
                .antMatchers( "/oauth2/**", "/login/**" ).permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .tokenEndpoint().accessTokenResponseClient( this::getTokenResponse );
    }

    private OAuth2AccessTokenResponse getTokenResponse( OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest ) {
        DefaultAuthorizationCodeTokenResponseClient client = new DefaultAuthorizationCodeTokenResponseClient();
        client.setRestOperations( restOperations() );
        return client.getTokenResponse( authorizationGrantRequest );
    }

    private RestOperations restOperations() {
        OAuth2AccessTokenResponseHttpMessageConverter converter = new OAuth2AccessTokenResponseHttpMessageConverter();
        converter.setTokenResponseConverter( getConverter() );
        RestTemplate restTemplate = new RestTemplate( Arrays.asList(
                new FormHttpMessageConverter(), converter ) );
        restTemplate.setErrorHandler( new OAuth2ErrorResponseErrorHandler() );
        return restTemplate;
    }

    private Converter< Map< String, String >, OAuth2AccessTokenResponse > getConverter() {
        return map -> OAuth2AccessTokenResponse.withToken( map.get( "id_token" ) )
                .tokenType( OAuth2AccessToken.TokenType.BEARER )
                .scopes( Set.of(map.get( "scope" )) )
                .expiresIn( Long.parseLong( map.get( "not_before" ) ) )
                .additionalParameters( Map.of("id_token", map.get( "id_token" )) )
                .build();
    }

}
