package com.jsblogs.security.jwt.init;

import com.jsblogs.security.jwt.entities.Role;
import com.jsblogs.security.jwt.entities.User;
import com.jsblogs.security.jwt.repositories.RoleRepository;
import com.jsblogs.security.jwt.repositories.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AppInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AppInitializer( UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run( ApplicationArguments args ) throws Exception {
        Role role = roleRepository.findByName( "ROLE_ADMIN" );
        if ( role == null ) {
            role = new Role();
            role.setName( "ROLE_ADMIN" );
            role = roleRepository.save( role );
        }

        User user = userRepository.findByEmail( "jsblogs@gmail.com" );
        if ( user == null ) {
            user = new User();
            user.setEmail( "jsblogs@gmail.com" );
            user.setPassword( passwordEncoder.encode( "admin" ) );
            user.setRoles( Collections.singleton( role ) );
            userRepository.save( user );
        }

    }
}
