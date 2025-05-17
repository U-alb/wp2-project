package org.wp2.medsys.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.wp2.medsys.domain.User;
import org.wp2.medsys.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired                          //sa-mi bag toata pl in beanu asta cu parole
    //lasa-l dreacu cu field injection
    //ca daca ii dai constructor injection a crapat tot
    private PasswordEncoder passwordEncoder;
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/new")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "adduser";
    }

    @PostMapping("/new")
    public String addUser(User user) {
        user.setPassHash(passwordEncoder.encode(user.getPassHash()));
        userService.save(user);
        return "redirect:/users";
    }
}
