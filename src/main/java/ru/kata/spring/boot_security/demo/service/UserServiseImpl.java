package ru.kata.spring.boot_security.demo.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public class UserServiseImpl implements UserService {
    ObjectMapper objectMapper = new ObjectMapper();

    private UserDao dao;
    private RoleService roleService;

    private PasswordEncoder encoder;

    @Autowired
    public UserServiseImpl(UserDao dao, RoleService roleService, PasswordEncoder encoder) {
        this.dao = dao;
        this.roleService = roleService;
        this.encoder = encoder;
    }


    @Override
    public UserDTO[] getAllUsers() {
        List<User> allUsers = dao.findAll();
        UserDTO[] result = new UserDTO[allUsers.size()];
        for (int i = 0; i < allUsers.size(); i++) {
            result[i] = toDTO(allUsers.get(i));
        }
        return result;
    }

    @Override
    public User getUser(Long id) {
        return dao.findById(id).get();
    }


    @Transactional
    @Override
    public void saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        dao.save(user);
    }

    @Transactional
    @Override
    public UserDTO[] updateUser(String userJsonString) {
        try {
            UserDTO userDTO = objectMapper.readValue(userJsonString, UserDTO.class);
            saveUser(fromDTO(userDTO));
        } catch (Exception e) {
            System.out.println(e);
        }
        return getAllUsers();
    }

    @Transactional
    @Override
    public UserDTO[] deleteUser(String id) {

        dao.deleteById(Long.parseLong(id));
        return getAllUsers();
    }

    @Override
    public UserDTO toDTO(User user) {
        UserDTO result = new UserDTO();
        result.setId(user.getId());
        result.setName(user.getName());
        result.setLastName(user.getLastName());
        result.setAge(user.getAge());
        result.setEmail(user.getEmail());
        result.setPassword("");
        result.setRoles(roleService.convertRolesToNames(user.getRoles()));
        return result;
    }

    @Override
    public User fromDTO(UserDTO userDTO) {
        return new User(userDTO.getId(),
                userDTO.getName(),
                userDTO.getLastName(),
                userDTO.getAge(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                roleService.convertNamesToRoles(userDTO.getRoles()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = dao.getUserByName(username);
        return user;
    }
}
