package com.example.ecom_app.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileServiceInterface {
    String uploadImage(String path, MultipartFile imageFile);
}
