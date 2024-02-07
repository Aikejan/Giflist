package com.example.giftlistb10.services;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

public interface StorageService {
    Map<String, String> uploadFile(MultipartFile file) throws IOException;

    Map<String, String> deleteObject(String s, String fileName);
}