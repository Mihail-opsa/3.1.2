package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> getAllRoles();

    void save(Role role);

    Object findAll();
    Optional<Role> findByName(String name);
    Optional<Role> findById(Long id);
}
