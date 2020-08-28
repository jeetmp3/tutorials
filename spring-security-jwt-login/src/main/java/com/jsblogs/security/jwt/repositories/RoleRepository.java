package com.jsblogs.security.jwt.repositories;

import com.jsblogs.security.jwt.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository< Role, Long > {

    Role findByName(String name);
}
