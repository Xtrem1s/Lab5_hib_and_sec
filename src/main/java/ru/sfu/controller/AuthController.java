package ru.sfu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sfu.model.User;
import ru.sfu.service.UserDetailsServiceImpl;

import javax.validation.Valid;

@Controller
public class AuthController {


    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") User user) {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") @Valid User userForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        if (!userDetailsService.saveUser(userForm)) {
            model.addAttribute("usernameOccupiedError", "User with this username already exists");
            return "registration";
        }
        return "redirect:/login";
    }

}
