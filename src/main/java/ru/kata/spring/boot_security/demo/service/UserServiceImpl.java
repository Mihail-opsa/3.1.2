
package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    @Override
    public void registerUserWithRoles(User user, Set<Long> roleIds) {
        Set<Role> roles = convertRoleIdsToRoles(roleIds);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        userDao.save(user);
    }

    @Transactional
    @Override
    public void updateUserWithRoles(Long id, User user, Set<Long> roleIds) {
        Set<Role> roles = convertRoleIdsToRoles(roleIds);
        User existingUser = userDao.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));


        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            user.setPassword(existingUser.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        user.setId(id);
        user.setRoles(roles);
        userDao.update(user);
    }

//    @Transactional
//    @Override
//    public void createUserWithDefaultRole(User user) {
//        Role userRole = roleDao.findByName("ROLE_USER")
//                .orElseThrow(() -> new RuntimeException("Default role not found"));
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setRoles(Set.of(userRole));
//        userDao.save(user);
//    }


    @Transactional
    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }


    private Set<Role> convertRoleIdsToRoles(Set<Long> roleIds) {
        return roleIds.stream()
                .map(roleId -> roleDao.findById(roleId)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleId)))
                .collect(Collectors.toSet());
    }


    @Transactional(readOnly = true)
    @Override
    public User getCurrentUser(Principal principal) {
        if (principal == null) {
            throw new RuntimeException("User not authenticated");
        }

        String username = principal.getName();
        return userDao.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }


}