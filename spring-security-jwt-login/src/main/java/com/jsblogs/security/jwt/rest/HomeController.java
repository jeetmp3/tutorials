package com.jsblogs.security.jwt.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/v1/home")
public class HomeController {

    @GetMapping
    public Map<String, String> home()
    {
        return Collections.singletonMap("name", "JSBLOGS");
    }
}
