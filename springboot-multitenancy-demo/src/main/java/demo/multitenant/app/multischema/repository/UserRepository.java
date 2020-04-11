package demo.multitenant.app.multischema.repository;

import demo.multitenant.app.multischema.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
