package kg.attractor.microgram.service;

import kg.attractor.microgram.dto.CommentDto;
import kg.attractor.microgram.exception.NotFoundException;
import kg.attractor.microgram.model.Comment;
import kg.attractor.microgram.model.Publication;
import kg.attractor.microgram.model.User;
import kg.attractor.microgram.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PublicationService publicationService;
    private final UserService userService;

    @Transactional
    public void addComment(
            Long publicationId,
            CommentDto commentDto,
            String login
    ) {
        User user = findAuthenticatedUser(login);
        Publication publication = publicationService.getById(publicationId);

        Comment comment = new Comment();
        comment.setPublication(publication);
        comment.setUser(user);
        comment.setText(commentDto.getText().trim());
        comment.setCreatedAt(LocalDateTime.now());

        commentRepository.saveAndFlush(comment);

        log.info(
                "Пользователь {} добавил комментарий {} к публикации {}",
                login,
                comment.getId(),
                publicationId
        );
    }

    @Transactional(readOnly = true)
    public List<Comment> getByPublication(Long publicationId) {
        publicationService.getById(publicationId);

        return commentRepository
                .findByPublication_IdOrderByCreatedAtAscIdAsc(
                        publicationId
                );
    }

    @Transactional(readOnly = true)
    public long countComments(Long publicationId) {
        return commentRepository.countByPublication_Id(
                publicationId
        );
    }

    @Transactional
    public void deleteComment(
            Long publicationId,
            Long commentId,
            String login
    ) {
        User user = findAuthenticatedUser(login);

        Comment comment = commentRepository
                .findByIdAndPublication_Id(
                        commentId,
                        publicationId
                )
                .orElseThrow(() -> new NotFoundException(
                        "Комментарий не найден"
                ));

        Long publicationOwnerId = comment.getPublication()
                .getUser()
                .getId();

        if (!publicationOwnerId.equals(user.getId())) {
            log.warn(
                    "Пользователь {} попытался удалить комментарий {} "
                            + "под чужой публикацией {}",
                    login,
                    commentId,
                    publicationId
            );

            throw new AccessDeniedException(
                    "Удалять комментарии может только владелец публикации"
            );
        }

        commentRepository.delete(comment);
        commentRepository.flush();

        log.info(
                "Пользователь {} удалил комментарий {} из публикации {}",
                login,
                commentId,
                publicationId
        );
    }

    private User findAuthenticatedUser(String login) {
        if (login == null || login.isBlank()) {
            throw new AccessDeniedException(
                    "Для работы с комментариями необходимо войти"
            );
        }

        return userService.getByLogin(login);
    }
}