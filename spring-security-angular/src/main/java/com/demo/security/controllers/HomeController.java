package com.demo.security.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping( "/v1/user" )
public class HomeController {

    @GetMapping("info")
    public Map< String, String > hello( @AuthenticationPrincipal( expression = "attributes['name']" ) String name ) {
        return Collections.singletonMap( "name", name );
    }
}
