package kg.attractor.microgram.repository;

import kg.attractor.microgram.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
    List<Publication> findByUserIdOrderByCreatedAtDescIdDesc(Long userId);
    long countByUserId(Long userId);

    @Query("select p from Publication p where p.user.id in " +
            "(select s.author.id from Subscription s where s.subscriber.id = :userId) " +
            "order by p.createdAt desc, p.id desc")
    List<Publication> findFeed(@Param("userId") Long userId);

    @Query(value = "select count(*) from publication_likes where publication_id = :id", nativeQuery = true)
    long countLikes(@Param("id") Long id);

    @Query(value = "select count(*) from comments where publication_id = :id", nativeQuery = true)
    long countComments(@Param("id") Long id);
}
