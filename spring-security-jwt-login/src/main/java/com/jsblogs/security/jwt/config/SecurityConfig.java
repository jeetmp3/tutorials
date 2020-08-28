package com.jsblogs.security.jwt.config;

import com.jsblogs.security.jwt.security.JwtTokenAuthenticationFilter;
import com.jsblogs.security.jwt.security.JwtTokenFilter;
import com.jsblogs.security.jwt.security.JwtTokenStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    private final JwtTokenStore jwtTokenStore;
    private final JwtTokenFilter jwtTokenFilter;

    public SecurityConfig( JwtTokenStore jwtTokenStore, JwtTokenFilter jwtTokenFilter ) {
        this.jwtTokenStore = jwtTokenStore;
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http.addFilterBefore( jwtTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class );
        http.addFilterBefore( jwtTokenFilter, JwtTokenAuthenticationFilter.class );
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers( "/login" ).permitAll()
                .anyRequest().authenticated()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint( this::commence )
        .and()
        .sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS );
    }

    private void commence( HttpServletRequest request, HttpServletResponse response,
                           AuthenticationException authException) throws IOException, ServletException {
        response.setContentType( "application/json" );
        response.getWriter().write( "{\"error\": \"User is not Auhtenticated\"}" );
    }

    @Bean
    public JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter() throws Exception {
        JwtTokenAuthenticationFilter filter = new JwtTokenAuthenticationFilter();
        filter.setAuthenticationManager( authenticationManagerBean() );
        filter.setAuthenticationSuccessHandler(this::onAuthenticationSuccess  );
        filter.setAuthenticationFailureHandler( this::onAuthenticationFailure );
        return filter;
    }

    private void onAuthenticationSuccess(HttpServletRequest request,
                                 HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        try {
            String token = jwtTokenStore.generateToken( authentication );
            response.setContentType( "application/json" );
            response.getWriter().write( "{\"accesToken\" : \""+token+"\"}" );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    private void onAuthenticationFailure(HttpServletRequest request,
                                 HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        response.setContentType( "application/json" );
        response.getWriter().write( "{\"error\": \"" + exception.getMessage() +"\" }" );
    }
}
