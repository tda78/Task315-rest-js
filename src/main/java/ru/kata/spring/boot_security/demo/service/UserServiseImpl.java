package ru.kata.spring.boot_security.demo.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public class UserServiseImpl implements UserService {
    private final ObjectMapper objectMapper;

    private final UserDao dao;
    private final RoleService roleService;

    private final PasswordEncoder encoder;

    @Autowired
    public UserServiseImpl(UserDao dao, RoleService roleService,
                           ObjectMapper objectMapper, PasswordEncoder encoder) {
        this.dao = dao;
        this.roleService = roleService;
        this.encoder = encoder;
        this.objectMapper = objectMapper;
    }


    @Override
    public List<UserDto> getAllUsers() {
        return dao.findAll().stream().map(user->toDTO(user)).toList();
    }

    @Override
    public UserDto getUser(Long id) {
        return toDTO(dao.findById(id).get());
    }

    @Override
    public UserDto getme(String name) {
        return toDTO((User) loadUserByUsername(name));
    }


    @Transactional
    @Override
    public void saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        dao.save(user);
    }

    @Transactional
    @Override
    public void updateUser(String userJsonString) {
        try {
            UserDto userDTO = objectMapper.readValue(userJsonString, UserDto.class);
            saveUser(fromDTO(userDTO));

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Transactional
    @Override
    public void deleteUser(String id) {
        dao.deleteById(Long.parseLong(id));
    }


    private UserDto toDTO(User user) {
        UserDto result = new UserDto();
        result.setId(user.getId());
        result.setName(user.getName());
        result.setLastName(user.getLastName());
        result.setAge(user.getAge());
        result.setEmail(user.getEmail());
        result.setPassword("");
        result.setRoles(roleService.convertRolesToNames(user.getRoles()));
        return result;
    }

    private User fromDTO(UserDto userDTO) {
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
