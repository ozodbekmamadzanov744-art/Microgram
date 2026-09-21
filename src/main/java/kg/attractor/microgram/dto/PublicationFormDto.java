package kg.attractor.microgram.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PublicationFormDto {
    private MultipartFile image;
    @NotBlank(message = "Введите описание")
    @Size(max = 2000, message = "Описание: не более 2000 символов")
    private String description;
}
