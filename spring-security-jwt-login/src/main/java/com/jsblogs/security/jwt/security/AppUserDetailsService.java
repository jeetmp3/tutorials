package com.jsblogs.security.jwt.security;

import com.jsblogs.security.jwt.entities.Role;
import com.jsblogs.security.jwt.entities.User;
import com.jsblogs.security.jwt.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AppUserDetailsService  implements UserDetailsService {
    private final UserRepository userRepository;

    public AppUserDetailsService( UserRepository userRepository ) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        User u = userRepository.findByEmail( username );
        if(u == null) {
            throw new UsernameNotFoundException("User does not exist with email: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                u.getEmail(), u.getPassword(),
                u.getRoles().stream().map( Role::getName )
                .map( SimpleGrantedAuthority::new )
                .collect( Collectors.toSet())
        );
    }
}
