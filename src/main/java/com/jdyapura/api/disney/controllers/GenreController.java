package com.jdyapura.api.disney.controllers;

import com.jdyapura.api.disney.entities.Genre;
import com.jdyapura.api.disney.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private final String PATH_IMAGES = ".//src//main//resources//files//";

    @Autowired
    private GenreService service;

    @GetMapping
    public ResponseEntity<List<Genre>> getAllGenres() {
        try {
            return new ResponseEntity<>(service.findAllGenres(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenre(@PathVariable("id") int idGenre){
        try {
            return new ResponseEntity<>(service.findGenreById(idGenre), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping
    public ResponseEntity<?> postGenre(@RequestBody Genre newGenre, @RequestParam MultipartFile imageFile) {

        if (imageFile.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Seleccionar archivo");
        }

        try {
            byte[] bytes = imageFile.getBytes();
            Path path = Paths.get(PATH_IMAGES + imageFile.getOriginalFilename());
            Files.write(path, bytes);

            newGenre.setImage(path.toString());

            return new ResponseEntity<>(service.saveGenre(newGenre), HttpStatus.CREATED);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genre> updateGenre(@PathVariable("id") int idGenre, @RequestBody Genre updatedGenre) {
        return new ResponseEntity<>(service.updateGenre(idGenre, updatedGenre), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable("id") int idGenre){
        service.deleteGenre(idGenre);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
