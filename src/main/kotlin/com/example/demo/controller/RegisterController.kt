package com.example.demo.controller

import com.example.demo.model.User
import com.example.demo.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class RegisterController(private val userService: UserService) {

    @GetMapping("/registerForm")
    fun registerForm(model: Model): String {
        model.addAttribute("user", User())
        return "register"
    }

    @PostMapping("/register")
    fun register(@ModelAttribute("user") user: User,bindingResult: BindingResult,
                 redirectAttributes: RedirectAttributes
    ): String {

        if (bindingResult.hasErrors()) {
            return "register"
        }

        userService.createUser(user)
        redirectAttributes.addFlashAttribute("message", "Registration successful")

        return "redirect:/login"
    }

    @GetMapping("/register")
    fun showRegistrationForm(model: Model): String {
        model.addAttribute("user", User())
        return "register"
    }
}