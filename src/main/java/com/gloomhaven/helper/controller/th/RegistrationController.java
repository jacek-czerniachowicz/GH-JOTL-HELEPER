package com.gloomhaven.helper.controller.th;

import com.gloomhaven.helper.model.dto.UserDTO;
import com.gloomhaven.helper.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {
    @Autowired
    private IUserService IUserService;

    @GetMapping
    public String showRegister(Model model) {
        model.addAttribute("user", new UserDTO());
        return "register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("user") UserDTO userDTO) {
        if(IUserService.createUser(userDTO))
            return "redirect:/register?success";
        else return "redirect:/register?error";
    }
}
