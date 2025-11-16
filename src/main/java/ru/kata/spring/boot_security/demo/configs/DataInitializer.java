package ru.kata.spring.boot_security.demo.configs;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class DataInitializer {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {

        if (roleService.findByName("ROLE_USER").isEmpty()) {
            // Создаем роли
            Role roleUser = new Role("ROLE_USER");
            Role roleAdmin = new Role("ROLE_ADMIN");

            roleService.save(roleUser);
            roleService.save(roleAdmin);

            // Создаем пользователей
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user"));
            user.setName("User");
            user.setLastName("Userov");
            user.setAge((byte)25);
            user.setRoles(Set.of(roleUser));

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setName("Admin");
            admin.setLastName("Adminov");
            admin.setAge((byte)30);
            admin.setRoles(Set.of(roleUser, roleAdmin));

            userService.save(user);
            userService.save(admin);

            System.out.println("=== Test users created ===");
            System.out.println("User: user / user");
            System.out.println("Admin: admin / admin");
        }
    }
}