package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserDto> getAllUsers();

    UserDto getUser(Long id);
    UserDto getme(String name);

    void saveUser(User user);

    void updateUser(String userJsonString);

    void deleteUser(String stringId);
}
