package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

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
    public String getAllUsers(Model model) {
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
    public String create(@ModelAttribute("user") User user,
                         @RequestParam(value = "selectRoles") String[] roles) {
        user.setRoles(roleService.convertNamesToRoles(roles));
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
    public String submitUpdate(@ModelAttribute("user") User user,
                               @RequestParam("id") long id,
                               @RequestParam(value = "selectRoles") String[] roles) {
        user.setId(id);
        user.setRoles(roleService.convertNamesToRoles(roles));
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
