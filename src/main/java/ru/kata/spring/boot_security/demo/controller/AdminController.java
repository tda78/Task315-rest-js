package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;


@RestController
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService service) {
        this.userService = service;

    }

    @GetMapping("/getMe")
    public UserDTO getMe(Principal principal) {

        return userService.toDTO((User) userService.loadUserByUsername(principal.getName()));
    }

    @GetMapping("/getAllUsers")
    public UserDTO[] getAllUsers() {
        return userService.getAllUsers();
    }

    @PatchMapping("/update")
    public UserDTO[] saveUser(@RequestBody String userJsonString) {
        return userService.updateUser(userJsonString);
    }

    @DeleteMapping("/delete")
    public UserDTO[] deleteUser(@RequestBody String stringId) {
        return userService.deleteUser(stringId);
    }
}
