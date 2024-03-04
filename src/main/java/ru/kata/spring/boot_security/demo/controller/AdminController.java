package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService service, RoleService roleService) {
        this.userService = service;
        this.roleService = roleService;
    }

    @GetMapping("/getRoles")
    public List<String> getAllRoles() {
        return roleService.getAllRoles();
    }


    @GetMapping("/getMe")
    public UserDto getMe(Principal principal) {
        return userService.getme(principal.getName());
    }

    @GetMapping("/getAllUsers")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PatchMapping("/update")
    public List<UserDto> saveUser(@RequestBody String userJsonString) {
        userService.updateUser(userJsonString);
        return userService.getAllUsers();
    }

    @DeleteMapping("/delete")
    public List<UserDto> deleteUser(@RequestBody String stringId) {
        userService.deleteUser(stringId);
        return getAllUsers();
    }
}
