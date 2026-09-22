package kg.attractor.microgram.repository;

import kg.attractor.microgram.model.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository
        extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = {"user"})
    List<Comment>
    findByPublication_IdOrderByCreatedAtAscIdAsc(
            Long publicationId
    );

    Optional<Comment> findByIdAndPublication_Id(
            Long id,
            Long publicationId
    );

    long countByPublication_Id(
            Long publicationId
    );
}