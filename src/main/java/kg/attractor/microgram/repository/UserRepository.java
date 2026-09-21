package kg.attractor.microgram.repository;

import kg.attractor.microgram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginIgnoreCase(String login);
    Optional<User> findByEmailIgnoreCase(String email);
    boolean existsByLoginIgnoreCase(String login);
    boolean existsByEmailIgnoreCase(String email);
    List<User> findByLoginContainingIgnoreCaseOrNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrderByLoginAsc(
            String login, String name, String email);
}
