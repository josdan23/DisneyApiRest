package com.jdyapura.api.disney.controllers;

import com.jdyapura.api.disney.models.Genre;
import com.jdyapura.api.disney.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenreService service;

    @GetMapping
    public ResponseEntity<List<Genre>> getAllGenres() {
        return new ResponseEntity<>(service.findAllGenres(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenre(@PathVariable("id") int idGenre){
        return new ResponseEntity<>(service.findGenreById(idGenre), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Genre> postGenre(@RequestBody Genre newGenre) {
        return new ResponseEntity<>(service.saveGenre(newGenre), HttpStatus.CREATED);
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
