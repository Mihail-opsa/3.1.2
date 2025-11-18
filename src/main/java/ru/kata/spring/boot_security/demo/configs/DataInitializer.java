package ru.kata.spring.boot_security.demo.configs;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


import javax.annotation.PostConstruct;
import java.util.Set;


@Component
public class DataInitializer {
    private final UserService userService;
    private final RoleService roleService;


    public DataInitializer(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;

    }

    @PostConstruct
    public void init() {
        // Создаем роли если их нет
        if (roleService.findByName("ROLE_USER").isEmpty()) {
            roleService.save(new Role("ROLE_USER"));
        }
        if (roleService.findByName("ROLE_ADMIN").isEmpty()) {
            roleService.save(new Role("ROLE_ADMIN"));
        }

        // Создаем пользователя если его нет
        if (userService.findByUsername("user").isEmpty()) {
            User user = new User();
            user.setUsername("user");
            user.setPassword("user");
            user.setName("User");
            user.setLastName("Userov");
            user.setAge((byte) 25);

            // Используем ID ролей вместо объектов
            userService.registerUserWithRoles(user, Set.of(1L)); // ROLE_USER
        }

        // Создаем админа если его нет
        if (userService.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setName("Admin");
            admin.setLastName("Adminov");
            admin.setAge((byte) 30);

            // Используем ID ролей
            userService.registerUserWithRoles(admin, Set.of(1L, 2L)); // ROLE_USER + ROLE_ADMIN
        }

        System.out.println("=== Test users created ===");
        System.out.println("User: user / user");
        System.out.println("Admin: admin / admin");
    }
}