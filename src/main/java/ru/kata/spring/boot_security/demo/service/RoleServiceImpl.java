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
public class RoleServiceImpl implements RoleService{
    private RoleDao roleRepository;

    public RoleServiceImpl() {
    }

    @Autowired
    public RoleServiceImpl(RoleDao roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findRole(String name) {
        return roleRepository.findRoleByName(name);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> convertNamesToRoles(String[] names) {
        List<Role> userRoles = new ArrayList<>();
        for (String s : names) userRoles.add(findRole(s));
        return userRoles;
    }
    @Override
    public String[] convertRolesToNames(List<Role> roles){
        String[] names = new String[roles.size()];
        for (int i = 0; i<roles.size(); i++){
            names[i] = roles.get(i).getName();
        }
        return names;
    }
}
