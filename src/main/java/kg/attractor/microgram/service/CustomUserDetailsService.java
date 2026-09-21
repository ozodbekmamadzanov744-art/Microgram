package kg.attractor.microgram.service;

import kg.attractor.microgram.model.User;
import kg.attractor.microgram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String value = username.trim();
        User user;
        if (value.contains("@")) {
            user = userRepository.findByEmailIgnoreCase(value)
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        } else {
            user = userRepository.findByLoginIgnoreCase(value)
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        }
        return org.springframework.security.core.userdetails.User.withUsername(user.getLogin())
                .password(user.getPassword()).roles("USER").build();
    }
}
