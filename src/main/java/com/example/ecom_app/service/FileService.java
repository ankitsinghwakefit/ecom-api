package com.example.ecom_app.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService implements FileServiceInterface {
    public String uploadImage(String path, MultipartFile imageFile) {
        String originalImageName = imageFile.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String newImageName = randomId.concat(originalImageName.substring(originalImageName.lastIndexOf('.')));
        String filePath = path + File.separator + newImageName;

        // check if path already exists if not create
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // save the file to given path
        try {
            Files.copy(imageFile.getInputStream(), Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newImageName;
    }
}
