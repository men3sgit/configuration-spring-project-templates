package com.example.unsplash.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface FileUploadService {

    public void storeFile(MultipartFile file) throws IOException;
    public byte[] readFile(String path) throws IOException;


}
