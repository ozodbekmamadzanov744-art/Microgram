package kg.attractor.microgram.controller;

import jakarta.validation.Valid;
import kg.attractor.microgram.dto.PublicationFormDto;
import kg.attractor.microgram.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class PublicationController {
    private final PublicationService publicationService;

    @GetMapping("/")
    public String home(Principal principal) {
        return principal == null ? "redirect:/users/search" : "redirect:/publications";
    }

    @GetMapping("/publications")
    public String feed(Model model, Principal principal) {
        model.addAttribute("publications", publicationService.getFeed(principal.getName()));
        return "publications/feed";
    }

    @GetMapping("/publications/new")
    public String create(Model model) {
        model.addAttribute("publicationFormDto", new PublicationFormDto());
        return "publications/form";
    }

    @PostMapping("/publications")
    public String create(@Valid @ModelAttribute("publicationFormDto") PublicationFormDto dto,
                         BindingResult bindingResult, Principal principal) throws IOException {
        if (dto.getImage() == null || dto.getImage().isEmpty()) {
            bindingResult.rejectValue("image", "image", "Выберите картинку");
        }
        if (bindingResult.hasErrors()) {
            return "publications/form";
        }
        try {
            Long id = publicationService.create(dto, principal.getName());
            return "redirect:/publications/" + id;
        } catch (IllegalArgumentException exception) {
            bindingResult.rejectValue("image", "image", exception.getMessage());
            return "publications/form";
        }
    }

    @GetMapping("/publications/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("publication", publicationService.getById(id));
        model.addAttribute("likeCount", publicationService.countLikes(id));
        model.addAttribute("commentCount", publicationService.countComments(id));
        return "publications/details";
    }

    @PostMapping("/publications/{id}/delete")
    public String delete(@PathVariable Long id, Principal principal) {
        publicationService.delete(id, principal.getName());
        return "redirect:/publications";
    }
}
