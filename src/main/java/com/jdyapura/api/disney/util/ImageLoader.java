package com.jdyapura.api.disney.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageLoader {

    public static final String PATH_IMAGES = "./src/main/resources/static/";

    public static String getStringPathOfImageUpload(MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {

            byte[] bytes = imageFile.getBytes();
            Path path = Paths.get(PATH_IMAGES + imageFile.getOriginalFilename());
            Files.write(path, bytes);
            return path.toString();

        } else {
            return "no image";
        }
    }
}
