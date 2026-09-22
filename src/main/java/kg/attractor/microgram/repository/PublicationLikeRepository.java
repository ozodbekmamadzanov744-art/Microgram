package kg.attractor.microgram.repository;

import kg.attractor.microgram.model.PublicationLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicationLikeRepository
        extends JpaRepository<PublicationLike, Long> {

    boolean existsByPublication_IdAndUser_Id(
            Long publicationId,
            Long userId
    );

    long countByPublication_Id(
            Long publicationId
    );
}