package kg.attractor.microgram.service;

import kg.attractor.microgram.dto.PublicationFormDto;
import kg.attractor.microgram.exception.NotFoundException;
import kg.attractor.microgram.model.Publication;
import kg.attractor.microgram.model.User;
import kg.attractor.microgram.repository.PublicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicationService {
    private final PublicationRepository publicationRepository;
    private final UserService userService;
    private final FileService fileService;

    public Publication getById(Long id) {
        return publicationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Публикация не найдена"));
    }

    public List<Publication> getByUser(Long userId) {
        return publicationRepository.findByUserIdOrderByCreatedAtDescIdDesc(userId);
    }

    public List<Publication> getFeed(String login) {
        return publicationRepository.findFeed(userService.getByLogin(login).getId());
    }

    public Long create(PublicationFormDto dto, String login) throws IOException {
        User user = userService.getByLogin(login);
        String image = fileService.saveImage(dto.getImage());
        Publication publication = new Publication();
        publication.setUser(user);
        publication.setImage(image);
        publication.setDescription(dto.getDescription().trim());
        publication.setCreatedAt(LocalDateTime.now());
        try {
            publicationRepository.saveAndFlush(publication);
        } catch (RuntimeException exception) {
            try {
                fileService.deleteImage(image);
            } catch (IOException cleanupException) {
                log.error("Не удалось удалить файл {} после ошибки сохранения", image, cleanupException);
            }
            throw exception;
        }
        log.info("Пользователь {} создал публикацию {}", login, publication.getId());
        return publication.getId();
    }

    public void delete(Long id, String login) {
        Publication publication = getById(id);
        if (!publication.getUser().getId().equals(userService.getByLogin(login).getId())) {
            throw new AccessDeniedException("Удалить публикацию может только её автор");
        }
        publicationRepository.delete(publication);
        try {
            fileService.deleteImage(publication.getImage());
        } catch (IOException exception) {
            log.error("Пользователь {}: не удалось удалить файл публикации {}", login, id, exception);
        }
        log.info("Пользователь {} удалил публикацию {}", login, id);
    }

    public long countByUser(Long userId) {
        return publicationRepository.countByUserId(userId);
    }

    public long countLikes(Long id) {
        return publicationRepository.countLikes(id);
    }

    public long countComments(Long id) {
        return publicationRepository.countComments(id);
    }
}
