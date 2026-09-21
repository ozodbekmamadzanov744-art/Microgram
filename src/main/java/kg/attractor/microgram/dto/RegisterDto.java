package kg.attractor.microgram.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {
    @NotBlank(message = "Введите логин")
    @Pattern(regexp = "[a-zA-Z0-9_]{3,30}", message = "Логин: 3–30 латинских букв, цифр или символов _")
    private String login;
    @NotBlank(message = "Введите email")
    @Email(message = "Некорректный email")
    @Size(max = 255, message = "Email слишком длинный")
    private String email;
    @NotBlank(message = "Введите пароль")
    @Size(min = 6, max = 50, message = "Пароль: 6–50 символов")
    @Pattern(regexp = "[\\x21-\\x7E]+", message = "Пароль: латинские буквы, цифры и знаки без пробелов")
    private String password;
    @NotBlank(message = "Введите имя")
    @Size(max = 100, message = "Имя: не более 100 символов")
    private String name;
    @Size(max = 1000, message = "Информация: не более 1000 символов")
    private String information;
}
