package com.demo.security.config;

import com.demo.security.TokenFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

@EnableWebSecurity
@Configuration
public class SecuityConfig extends WebSecurityConfigurerAdapter {

    private final InMemoryTokenHandler inMemoryTokenHandler;
    private final TokenFilter tokenFilter;
    private final ObjectMapper objectMapper;

    public SecuityConfig( InMemoryTokenHandler inMemoryTokenHandler, TokenFilter tokenFilter, ObjectMapper objectMapper ) {
        this.inMemoryTokenHandler = inMemoryTokenHandler;
        this.tokenFilter = tokenFilter;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http.cors()
                .and()
                .authorizeRequests().antMatchers( "/oauth2/**", "/login/**" ).permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .authorizationRequestRepository( new ReqRepository() )
                .and()
                .successHandler( this::successHandler )
                .and()
                .exceptionHandling()
                .authenticationEntryPoint( this::failureHandler );

        http.addFilterBefore( tokenFilter, UsernamePasswordAuthenticationFilter.class );
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins( Collections.singletonList( "*" ) );
        configuration.setAllowedHeaders( Collections.singletonList( "*" ) );
        configuration.setAllowedMethods( Collections.singletonList( "*" )  );
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration( "/**", configuration );
        return source;
    }

    private void successHandler( HttpServletRequest req, HttpServletResponse resp, Authentication auth ) throws IOException {
        String token = inMemoryTokenHandler.saveAndGetToken( auth );
        resp.setHeader( "Content-Type", "application/json" );
        resp.getWriter().write( objectMapper.writeValueAsString( Collections.singletonMap( "access-token", token ) ) );
    }

    public void failureHandler( HttpServletRequest request,
                                HttpServletResponse response, AuthenticationException exception ) throws IOException {
        response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
        response.getWriter().write( "Error" );
    }
}
