package com.demo.security.config;

import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ReqRepository implements AuthorizationRequestRepository< OAuth2AuthorizationRequest > {

    private Map< String, OAuth2AuthorizationRequest > requestCache = new HashMap<>();

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest( HttpServletRequest request ) {
        String state = request.getParameter( "state" );
        if ( state != null ) {
            return removeAuthorizationRequest( request );
        }
        return null;
    }

    @Override
    public void saveAuthorizationRequest( OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response ) {
        String state = authorizationRequest.getState();
        requestCache.put( state, authorizationRequest );
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest( HttpServletRequest request ) {
        String state = request.getParameter( "state" );
        return requestCache.remove( state );
    }
}
