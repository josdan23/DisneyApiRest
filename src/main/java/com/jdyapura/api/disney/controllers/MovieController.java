package com.jdyapura.api.disney.controllers;

import com.jdyapura.api.disney.models.Movie;
import com.jdyapura.api.disney.services.MovieService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {


    @Autowired
    private MovieService service;

    @GetMapping
    public ResponseEntity<Iterable<Movie>> getAllMovies() {

        return new ResponseEntity(service.findAllMovies(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable("id") int idMovie) {
        return new ResponseEntity<>(service.findMovieById(idMovie), HttpStatus.OK);
    }
}
