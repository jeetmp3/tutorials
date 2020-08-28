package com.jsblogs.security.jwt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private ObjectMapper mapper = new ObjectMapper();
    private ThreadLocal<UserInfo> tl = new ThreadLocal<>();

    public JwtTokenAuthenticationFilter() {

    }

    @Override
    public Authentication attemptAuthentication( HttpServletRequest request, HttpServletResponse response ) throws AuthenticationException {
        extractUsernamePassword( request );
        return super.attemptAuthentication( request, response );
    }

    @Override
    protected String obtainPassword( HttpServletRequest request ) {
        UserInfo info = tl.get();
        if(info != null) {
            return info.getPassword();
        }
        return null;
    }

    @Override
    protected String obtainUsername( HttpServletRequest request ) {
        UserInfo info = tl.get();
        if(info != null) {
            return info.getUsername();
        }
        return null;
    }

    void extractUsernamePassword(HttpServletRequest request) {
        try {
            UserInfo info = mapper.readValue( request.getInputStream(), UserInfo.class );
            if(info != null) {
                tl.set( info );
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    static class UserInfo {
        private String username;
        private String password;


        public String getUsername() {
            return username;
        }

        public void setUsername( String username ) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword( String password ) {
            this.password = password;
        }
    }
}
