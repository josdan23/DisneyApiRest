package com.jdyapura.api.disney.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdyapura.api.disney.entities.Genre;
import com.jdyapura.api.disney.services.GenreService;
import com.jdyapura.api.disney.util.ImageLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private final String PATH_IMAGES = "./src/main/resources/static/";

    private final ObjectMapper mapper = new ObjectMapper();


    @Autowired
    private GenreService service;

    @GetMapping
    public ResponseEntity<?> getAllGenres() {
        try {
            return new ResponseEntity<>(service.findAllGenres(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("No content", HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGenre(@PathVariable("id") int idGenre){
        try {
            return new ResponseEntity<>(service.findGenreById(idGenre), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("no content", HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity<Object> postGenre(
            @RequestParam String jsondata,
            @RequestParam(required = false) MultipartFile imageFile) {

       try {

            Genre newGenre = mapper.readValue(jsondata, Genre.class);

            newGenre.setImage(ImageLoader.getStringPathOfImageUpload(imageFile));

            return new ResponseEntity<>(service.saveGenre(newGenre), HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGenre(
            @PathVariable("id") int idGenre,
            @RequestParam String jsondata,
            @RequestParam(required = false) MultipartFile imageFile) {

        try {
            Genre genreToUpdate = mapper.readValue(jsondata, Genre.class);

            if ( imageFile != null && !imageFile.isEmpty()) {
                byte[] bytes = imageFile.getBytes();
                Path path = Paths.get(PATH_IMAGES + imageFile.getOriginalFilename());
                Files.write(path, bytes);
                genreToUpdate.setImage(path.toString());
            }

            return new ResponseEntity<>(service.updateGenre(idGenre, genreToUpdate), HttpStatus.OK);

        } catch (JsonProcessingException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable("id") int idGenre){
        service.deleteGenre(idGenre);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
