package com.example.giftlistb10.api;

import com.example.giftlistb10.services.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/storages")
@RequiredArgsConstructor
@Tag(name="S3File Api")
@CrossOrigin(origins = "*",maxAge = 3600)
public class StorageApi {

    private final StorageService s3Service;

    @Operation(summary = "Загрузить файл на s3", description = "Пользователь может загрузить файл")
    @PostMapping(path = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Map<String, String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return s3Service.uploadFile(file);
    }

    @Operation(summary = "Удалить файл из s3", description = "Пользователь может удалить файл")
    @DeleteMapping(path = "/delete")
    Map<String, String> deleteObject(@RequestParam("fileName") String fileName) throws IOException {
        return s3Service.deleteObject("my-bucket", fileName);
    }
}