package kg.attractor.microgram.repository;

import kg.attractor.microgram.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    boolean existsBySubscriberIdAndAuthorId(Long subscriberId, Long authorId);
    Optional<Subscription> findBySubscriberIdAndAuthorId(Long subscriberId, Long authorId);
    long countBySubscriberId(Long subscriberId);
    long countByAuthorId(Long authorId);
}
