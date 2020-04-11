package demo.multitenant.app.multischema.controllers;

import demo.multitenant.app.multischema.entity.User;
import demo.multitenant.app.multischema.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public Long createUser(String name, String email) {
        User user = new User(name, email);
        user = userRepository.save(user);
        return user.getId();
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
