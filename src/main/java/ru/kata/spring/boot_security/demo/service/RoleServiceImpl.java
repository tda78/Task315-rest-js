package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleRepository;

    @Autowired
    public RoleServiceImpl(RoleDao roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findRole(String name) {
        return roleRepository.findRoleByName(name);
    }

    @Override
    public List<String> getAllRoles() {
        return (convertRolesToNames(roleRepository.findAll()));
    }

    @Override
    public List<Role> convertNamesToRoles(List<String> names) {
        List<Role> userRoles = new ArrayList<>();
        for (String s : names) {
            s = "ROLE_" + s;
            userRoles.add(findRole(s));
        }
        return userRoles;
    }

    @Override
    public List<String> convertRolesToNames(List<Role> roles) {
        return roles.stream().map(Role::toString).toList();
    }
}
