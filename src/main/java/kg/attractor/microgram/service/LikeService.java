package kg.attractor.microgram.service;

import kg.attractor.microgram.model.Publication;
import kg.attractor.microgram.model.PublicationLike;
import kg.attractor.microgram.model.User;
import kg.attractor.microgram.repository.PublicationLikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {

    private final PublicationLikeRepository publicationLikeRepository;
    private final PublicationService publicationService;
    private final UserService userService;

    @Transactional
    public void addLike(Long publicationId, String login) {
        if (login == null || login.isBlank()) {
            throw new AccessDeniedException(
                    "Для добавления лайка необходимо войти"
            );
        }

        User user = userService.getByLogin(login);
        Publication publication = publicationService.getById(publicationId);

        boolean alreadyLiked =
                publicationLikeRepository.existsByPublication_IdAndUser_Id(
                        publicationId,
                        user.getId()
                );

        if (alreadyLiked) {
            log.debug(
                    "Пользователь {} уже поставил лайк публикации {}",
                    login,
                    publicationId
            );
            return;
        }

        PublicationLike publicationLike = new PublicationLike();
        publicationLike.setPublication(publication);
        publicationLike.setUser(user);
        publicationLike.setCreatedAt(LocalDateTime.now());

        publicationLikeRepository.saveAndFlush(publicationLike);

        log.info(
                "Пользователь {} поставил лайк публикации {}",
                login,
                publicationId
        );
    }

    @Transactional(readOnly = true)
    public long countLikes(Long publicationId) {
        return publicationLikeRepository.countByPublication_Id(
                publicationId
        );
    }

    @Transactional(readOnly = true)
    public boolean hasLiked(Long publicationId, String login) {
        if (login == null || login.isBlank()) {
            return false;
        }

        User user = userService.getByLogin(login);

        return publicationLikeRepository.existsByPublication_IdAndUser_Id(
                publicationId,
                user.getId()
        );
    }
}