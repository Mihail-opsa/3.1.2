package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Role> getAllRoles() {
        return roleDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Role getRoleByName(String name) {
        return roleDao.findByName(name)
                .orElseThrow(() -> new RuntimeException("Role not found: " + name));
    }

    @Transactional
    @Override
    public void save(Role role) {
        roleDao.save(role);
    }

    @Override
    public Object findAll() {
        return  roleDao.findAll();
    }
    @Transactional(readOnly = true)
    @Override
    public Optional<Role> findByName(String name) {
        return roleDao.findByName(name);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Role> findById(Long id) {
        return roleDao.findById(id);
    }


}