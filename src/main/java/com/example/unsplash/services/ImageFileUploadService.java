package com.example.unsplash.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;
@Service
public class ImageFileUploadService implements FileUploadService {
    private static final String UPLOAD_DIR = "upload";

    @Override
    public void storeFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("File is empty");
            }
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String generatedFileName = uploadPath.
                    resolve(UUID.randomUUID().toString() + getFileExtension(Objects.requireNonNull(file.getOriginalFilename()))).toString();

            Path storeFile = Path.of(generatedFileName);

            Files.copy(file.getInputStream(), storeFile);

        } catch (Exception e) {
        }
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return fileName.substring(dotIndex);
    }

    @Override
    public byte[] readFile(String fileName)  {
        Path path = Path.of(UPLOAD_DIR + "/"+fileName);
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }

}
