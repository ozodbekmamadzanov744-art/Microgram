package kg.attractor.microgram.controller;

import jakarta.validation.Valid;
import kg.attractor.microgram.dto.CommentDto;
import kg.attractor.microgram.dto.PublicationFormDto;
import kg.attractor.microgram.service.CommentService;
import kg.attractor.microgram.service.LikeService;
import kg.attractor.microgram.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class PublicationController {

    private final PublicationService publicationService;
    private final LikeService likeService;
    private final CommentService commentService;

    @GetMapping("/")
    public String home(Principal principal) {
        return principal == null
                ? "redirect:/users/search"
                : "redirect:/publications";
    }

    @GetMapping("/publications")
    public String feed(Model model, Principal principal) {
        model.addAttribute(
                "publications",
                publicationService.getFeed(principal.getName())
        );

        return "publications/feed";
    }

    @GetMapping("/publications/new")
    public String create(Model model) {
        model.addAttribute(
                "publicationFormDto",
                new PublicationFormDto()
        );

        return "publications/form";
    }

    @PostMapping("/publications")
    public String create(
            @Valid
            @ModelAttribute("publicationFormDto")
            PublicationFormDto dto,
            BindingResult bindingResult,
            Principal principal
    ) throws IOException {
        if (dto.getImage() == null || dto.getImage().isEmpty()) {
            bindingResult.rejectValue(
                    "image",
                    "image",
                    "Выберите картинку"
            );
        }

        if (bindingResult.hasErrors()) {
            return "publications/form";
        }

        try {
            Long id = publicationService.create(
                    dto,
                    principal.getName()
            );

            return "redirect:/publications/" + id;
        } catch (IllegalArgumentException exception) {
            bindingResult.rejectValue(
                    "image",
                    "image",
                    exception.getMessage()
            );

            return "publications/form";
        }
    }

    @GetMapping("/publications/{id}")
    public String details(
            @PathVariable Long id,
            Model model,
            Principal principal
    ) {
        model.addAttribute(
                "commentDto",
                new CommentDto()
        );

        addPublicationData(id, model, principal);

        return "publications/details";
    }

    @PostMapping("/publications/{id}/likes")
    public String addLike(
            @PathVariable Long id,
            Principal principal
    ) {
        likeService.addLike(
                id,
                principal.getName()
        );

        return "redirect:/publications/" + id;
    }

    @PostMapping("/publications/{id}/comments")
    public String addComment(
            @PathVariable Long id,
            @Valid
            @ModelAttribute("commentDto")
            CommentDto commentDto,
            BindingResult bindingResult,
            Model model,
            Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            addPublicationData(id, model, principal);

            return "publications/details";
        }

        commentService.addComment(
                id,
                commentDto,
                principal.getName()
        );

        return "redirect:/publications/" + id;
    }

    @PostMapping(
            "/publications/{publicationId}/comments/{commentId}/delete"
    )
    public String deleteComment(
            @PathVariable Long publicationId,
            @PathVariable Long commentId,
            Principal principal
    ) {
        commentService.deleteComment(
                publicationId,
                commentId,
                principal.getName()
        );

        return "redirect:/publications/" + publicationId;
    }

    @PostMapping("/publications/{id}/delete")
    public String delete(
            @PathVariable Long id,
            Principal principal
    ) {
        publicationService.delete(
                id,
                principal.getName()
        );

        return "redirect:/publications";
    }

    private void addPublicationData(
            Long publicationId,
            Model model,
            Principal principal
    ) {
        String login = principal == null
                ? null
                : principal.getName();

        model.addAttribute(
                "publication",
                publicationService.getById(publicationId)
        );

        model.addAttribute(
                "likeCount",
                likeService.countLikes(publicationId)
        );

        model.addAttribute(
                "liked",
                likeService.hasLiked(publicationId, login)
        );

        model.addAttribute(
                "comments",
                commentService.getByPublication(publicationId)
        );

        model.addAttribute(
                "commentCount",
                commentService.countComments(publicationId)
        );
    }
}