package com.jsblogs.security.jwt.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenStore tokenStore;

    public JwtTokenFilter( JwtTokenStore tokenStore ) {
        this.tokenStore = tokenStore;
    }

    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain ) throws ServletException, IOException {
        String header = request.getHeader( "Authorization" );
        if ( header != null && header.contains( "Bearer" ) ) {
            String[] tokens = header.split( " " );
            if ( tokens.length > 1 ) {
                String jwt = tokens[ 1 ];
                try {
                    Authentication authentication = tokenStore.getAuth( jwt );
                    SecurityContextHolder.getContext().setAuthentication( authentication );
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
            }
        }
        filterChain.doFilter( request, response );
    }
}
