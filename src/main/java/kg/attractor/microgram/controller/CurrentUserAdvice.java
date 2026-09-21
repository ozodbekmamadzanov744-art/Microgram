package kg.attractor.microgram.controller;

import kg.attractor.microgram.model.User;
import kg.attractor.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.security.Principal;

@ControllerAdvice
@RequiredArgsConstructor
public class CurrentUserAdvice {
    private final UserService userService;

    @ModelAttribute("currentUser")
    public User currentUser(Principal principal) {
        if (principal == null) {
            return null;
        }
        return userService.getByLogin(principal.getName());
    }
}
