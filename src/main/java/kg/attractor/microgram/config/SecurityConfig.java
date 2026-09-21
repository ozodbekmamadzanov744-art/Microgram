package kg.attractor.microgram.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**", "/css/**", "/images/**", "/uploads/**", "/error").permitAll()
                .requestMatchers("/publications/new").authenticated()
                .requestMatchers(HttpMethod.GET, "/", "/users/**", "/publications/*").permitAll()
                .anyRequest().authenticated())
            .formLogin(form -> form
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .successHandler((request, response, authentication) -> {
                    log.info("Вход пользователя: {}", authentication.getName());
                    response.sendRedirect("/publications");
                })
                .failureHandler((request, response, exception) -> {
                    log.warn("Неудачная попытка входа, адрес: {}", request.getRemoteAddr());
                    response.sendRedirect("/auth/login?error");
                }).permitAll())
            .logout(logout -> logout.logoutUrl("/auth/logout")
                .addLogoutHandler((request, response, authentication) -> {
                    if (authentication != null) {
                        log.info("Выход пользователя: {}", authentication.getName());
                    }
                })
                .logoutSuccessUrl("/auth/login?logout"));
        return http.build();
    }
}
