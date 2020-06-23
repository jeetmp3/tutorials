package com.demo.security;

import com.demo.security.config.InMemoryTokenHandler;
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
public class TokenFilter extends OncePerRequestFilter {

    private final InMemoryTokenHandler inMemoryTokenHandler;

    public TokenFilter( InMemoryTokenHandler inMemoryTokenHandler ) {
        this.inMemoryTokenHandler = inMemoryTokenHandler;
    }

    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain ) throws ServletException, IOException {
        String token = request.getHeader( "Authorization" );
        if ( token != null ) {
            String[] tokens = token.split( " " );
            Authentication auth = inMemoryTokenHandler.getAuthObject( tokens[ 1 ] );
            if ( auth != null ) {
                SecurityContextHolder.getContext().setAuthentication( auth );
            }
        }
        filterChain.doFilter( request, response );
    }
}
