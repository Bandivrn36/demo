package com.example.demo.controller

import com.example.demo.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@Controller
class LoginController(
    private val authenticationManager: AuthenticationManager
) {

    @GetMapping("/login")
    fun login(model: Model): String {
        model.addAttribute("user", User())
        return "login"
    }

    @PostMapping("/login")
    fun login(@ModelAttribute("user") user: User,redirectAttributes: RedirectAttributes,
              session: HttpSession,response: HttpServletResponse
    ): String {

        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(user.username, user.password))

        SecurityContextHolder.getContext().authentication = authentication

        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
            SecurityContextHolder.getContext())

        redirectAttributes.addFlashAttribute("username", user.username)

        return "redirect:/files"
    }
}