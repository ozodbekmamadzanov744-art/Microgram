package kg.attractor.microgram.controller;

import kg.attractor.microgram.service.UserService;
import kg.attractor.microgram.service.PublicationService;
import kg.attractor.microgram.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final PublicationService publicationService;
    private final SubscriptionService subscriptionService;

    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "") String param, Model model) {
        model.addAttribute("param", param);
        model.addAttribute("users", userService.search(param));
        return "users/search";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model, Principal principal) {
        model.addAttribute("profile", userService.getById(id));
        model.addAttribute("publications", publicationService.getByUser(id));
        model.addAttribute("publicationCount", publicationService.countByUser(id));
        model.addAttribute("subscriptionCount", subscriptionService.countSubscriptions(id));
        model.addAttribute("subscriberCount", subscriptionService.countSubscribers(id));
        boolean subscribed = false;
        if (principal != null) {
            Long currentId = userService.getByLogin(principal.getName()).getId();
            subscribed = subscriptionService.isSubscribed(currentId, id);
        }
        model.addAttribute("subscribed", subscribed);
        return "users/profile";
    }

    @PostMapping("/{id}/subscribe")
    public String subscribe(@PathVariable Long id, Principal principal) {
        subscriptionService.subscribe(id, principal.getName());
        return "redirect:/users/" + id;
    }

    @PostMapping("/{id}/unsubscribe")
    public String unsubscribe(@PathVariable Long id, Principal principal) {
        subscriptionService.unsubscribe(id, principal.getName());
        return "redirect:/users/" + id;
    }
}
