package kg.attractor.microgram.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileService {
    public String saveImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Выберите картинку");
        }
        String type = file.getContentType();
        if (!"image/jpeg".equals(type) && !"image/png".equals(type)) {
            throw new IllegalArgumentException("Допустимы изображения JPG и PNG");
        }
        BufferedImage image;
        try (InputStream input = file.getInputStream()) {
            image = ImageIO.read(input);
        }
        if (image == null) {
            throw new IllegalArgumentException("Не удалось прочитать изображение");
        }
        if (image.getWidth() > 6000 || image.getHeight() > 6000) {
            throw new IllegalArgumentException("Размер изображения: не более 6000 × 6000");
        }
        Path directory = Path.of("uploads");
        Files.createDirectories(directory);
        String filename = UUID.randomUUID() + ".png";
        ImageIO.write(image, "png", directory.resolve(filename).toFile());
        return "/uploads/" + filename;
    }

    public void deleteImage(String image) throws IOException {
        if (image != null && image.startsWith("/uploads/")) {
            Path directory = Path.of("uploads").toAbsolutePath().normalize();
            Path file = directory.resolve(image.substring("/uploads/".length())).normalize();
            if (file.getParent().equals(directory)) {
                Files.deleteIfExists(file);
            }
        }
    }
}
