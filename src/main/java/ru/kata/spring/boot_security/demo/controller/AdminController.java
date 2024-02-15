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
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "users";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "newuser";
    }

    @PostMapping("/new")
    public String create(HttpServletRequest request){
    User user = new User(request.getParameter("edit_firstName")
            , request.getParameter("edit_lastName")
            , Integer.parseInt(request.getParameter("edit_age"))
            , request.getParameter("edit_name")
            , request.getParameter("edit_password"),
            new ArrayList<Role>()
    );
    List<String> roles = Arrays.asList(request.getParameterValues("selectRoles"));
        if (roles.contains("ROLE_ADMIN")) {
        user.getRoles().add(roleService.findRole("ROLE_ADMIN"));
    }
        if (roles.contains("ROLE_USER")) {
        user.getRoles().add(roleService.findRole("ROLE_USER"));
    }
        userService.saveUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/update")
    public String updateForm(Model model, @RequestParam("id") long id) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "userupdate";
    }

    @PostMapping("/update")
    public String submitUpdate(HttpServletRequest request) {
        User user = new User(Long.parseLong(request.getParameter("edit_userID"))
                , request.getParameter("edit_firstName")
                , request.getParameter("edit_lastName")
                , Integer.parseInt(request.getParameter("edit_age"))
                , request.getParameter("edit_name")
                , request.getParameter("edit_password"),
                new ArrayList<Role>()
        );
        List<String> roles = Arrays.asList(request.getParameterValues("selectRoles"));
        if (roles.contains("ROLE_ADMIN")) {
            user.getRoles().add(roleService.findRole("ROLE_ADMIN"));
        }
        if (roles.contains("ROLE_USER")) {
            user.getRoles().add(roleService.findRole("ROLE_USER"));
        }

        userService.updateUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/delete")
    public String deleteForm(Model model, @RequestParam("id") long id) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "deleteUser";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin/";
    }
}
