package kg.attractor.microgram.controller;

import jakarta.validation.Valid;
import kg.attractor.microgram.dto.RegisterDto;
import kg.attractor.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registerDto", new RegisterDto());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerDto") RegisterDto dto,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }
        try {
            userService.register(dto);
        } catch (IllegalArgumentException exception) {
            bindingResult.reject("registration", exception.getMessage());
            return "auth/register";
        } catch (DataIntegrityViolationException exception) {
            bindingResult.reject("registration", "Логин или email уже занят");
            return "auth/register";
        }
        return "redirect:/auth/login?registered";
    }
}
