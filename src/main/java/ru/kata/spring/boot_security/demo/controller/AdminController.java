package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService service, RoleService roleService) {
        this.userService = service;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String getAllUsers(Model model, Principal principal) {
        model.addAttribute("me", (User) userService.loadUserByUsername(principal.getName()));
        model.addAttribute("newUser", new User());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "users";
    }


    @PostMapping("/new")
    public String create(@ModelAttribute("newUser") User newUser) {
        userService.saveUser(newUser);
        return "redirect:/admin/";
    }

    @PatchMapping("/update")
    public String submitUpdate(@ModelAttribute("newUser") User user) {
        userService.updateUser(user);
        return "redirect:/admin/";
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin/";
    }
}
