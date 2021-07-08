package com.jdyapura.api.disney.controllers;

import com.jdyapura.api.disney.controllers.responsedto.MovieDetailResponse;
import com.jdyapura.api.disney.controllers.responsedto.MovieResponse;
import com.jdyapura.api.disney.entities.Character;
import com.jdyapura.api.disney.entities.Movie;
import com.jdyapura.api.disney.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {


    @Autowired
    private MovieService service;

    @GetMapping
    public ResponseEntity<?> getAllMovies(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "genre", required = false) Integer idGenre,
            @RequestParam(value = "order", required = false ) String order) {

        List<MovieResponse> response = new ArrayList<>();

        service.findAllMovies().forEach( m -> {
            MovieResponse movieResponse = new MovieResponse(m);
            response.add(movieResponse);
        });

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDetailResponse> getMovieById(@PathVariable("id") int idMovie) {

        Movie savedMovie = service.findMovieById(idMovie);
        List<Character> characterInMovieList = service.findCharactersInMovieByIdMovie(idMovie);

        MovieDetailResponse response = new MovieDetailResponse(savedMovie);

        characterInMovieList.forEach( character -> {
            response.getCharacters().add(character.getName());
        });


        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Movie> postMovie(@RequestBody Movie movie) {
        return new ResponseEntity<>(service.saveMovie(movie), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable("id") int idMovie) {

        service.deleteMovie(idMovie);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
