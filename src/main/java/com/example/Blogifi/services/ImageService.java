package com.example.Blogifi.services;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class ImageService {

    private static final String BASE_URL = "http://localhost:7777/Blogifi/api/";
    private static final String IMAGE_DIRECTORY = "src/main/resources/static/images/posts";
    private static final String IMAGE_URL = "images/posts/";

    // @PostConstruct is used to execute a method after the bean is initialized (after dependency injection).
    // runs only once per bean lifecycle, right after the constructor but before the bean is used.
    // Typically used for initialization logic like loading configs, setting default values, etc.
    @PostConstruct
    public void init() {
        Path directoryPath = Paths.get(IMAGE_DIRECTORY);
        if (Files.notExists(directoryPath)) {
            try {
                // Create a File/Directory if not Exists
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Save Image with a unique name.
    public String saveImage(MultipartFile image) throws IOException, NoSuchAlgorithmException {
        String uniqueFileName = generateUniqueName(image.getOriginalFilename());
        Path filePath = Paths.get(IMAGE_DIRECTORY, uniqueFileName);

        Files.write(filePath, image.getBytes());
        return BASE_URL + IMAGE_URL + uniqueFileName;
    }

    private String generateUniqueName(String originalFilename) throws NoSuchAlgorithmException{
        String timeStamp = String.valueOf(System.currentTimeMillis());
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest((originalFilename+timeStamp).getBytes());
        return Base64.getUrlEncoder().encodeToString(hash) + getFileExtention(originalFilename);
    }

    private String getFileExtention(String originalFilename) {
        return originalFilename.contains(".") ? originalFilename.substring(originalFilename.lastIndexOf(".")): "";
    }

}
