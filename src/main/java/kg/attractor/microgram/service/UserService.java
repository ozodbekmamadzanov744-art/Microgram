package kg.attractor.microgram.service;

import kg.attractor.microgram.dto.RegisterDto;
import kg.attractor.microgram.exception.NotFoundException;
import kg.attractor.microgram.model.User;
import kg.attractor.microgram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    public User getByLogin(String login) {
        return userRepository.findByLoginIgnoreCase(login)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    public void register(RegisterDto dto) {
        String login = dto.getLogin().trim().toLowerCase(java.util.Locale.ROOT);
        String email = dto.getEmail().trim().toLowerCase(java.util.Locale.ROOT);
        if (userRepository.existsByLoginIgnoreCase(login)) {
            throw new IllegalArgumentException("Этот логин уже занят");
        }
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new IllegalArgumentException("Этот email уже используется");
        }
        User user = new User();
        user.setLogin(login);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setName(dto.getName().trim());
        user.setInformation(dto.getInformation());
        user.setAvatar("/images/avatar.png");
        userRepository.saveAndFlush(user);
        log.info("Регистрация пользователя: id={}, login={}", user.getId(), user.getLogin());
    }

    public List<User> search(String param) {
        String value = param.trim();
        if (value.isEmpty()) {
            return List.of();
        }
        return userRepository.findByLoginContainingIgnoreCaseOrNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrderByLoginAsc(
                value, value, value);
    }
}
