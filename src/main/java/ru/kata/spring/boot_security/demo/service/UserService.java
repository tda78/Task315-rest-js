package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDTO[] getAllUsers();

    User getUser(Long id);

    void saveUser(User user);

    UserDTO[] updateUser(String userJsonString);

    UserDTO[] deleteUser(String stringId);

    UserDTO toDTO(User user);

    User fromDTO(UserDTO userDTO);

}
