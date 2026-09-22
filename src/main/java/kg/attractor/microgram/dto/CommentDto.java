package kg.attractor.microgram.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    @NotBlank(message = "Введите текст комментария")
    @Size(
            max = 2000,
            message = "Комментарий: не более 2000 символов"
    )
    private String text;
}