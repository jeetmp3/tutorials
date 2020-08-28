package com.jsblogs.security.jwt.repositories;

import com.jsblogs.security.jwt.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository< User, Long > {

    User findByEmail(String email);
}
