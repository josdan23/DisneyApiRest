package com.jdyapura.api.disney.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdyapura.api.disney.controllers.responsedto.MovieDetailResponse;
import com.jdyapura.api.disney.controllers.responsedto.MovieResponse;
import com.jdyapura.api.disney.entities.Character;
import com.jdyapura.api.disney.entities.Genre;
import com.jdyapura.api.disney.entities.Movie;
import com.jdyapura.api.disney.services.GenreService;
import com.jdyapura.api.disney.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping
public class MovieController {

    private final String PATH_IMAGES = "./src/main/resources/static/";

    @Autowired
    private MovieService service;

    @Autowired
    private GenreService genreService;

    @GetMapping("/movies")
    public ResponseEntity<?> getAllMovies(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "genre", required = false) Integer idGenre,
            @RequestParam(value = "order", required = false ) String order) {

        if (name != null)
            return new ResponseEntity<>(service.findMovieByName(name), HttpStatus.OK);

        if (idGenre != null)
            return new ResponseEntity<>(service.findMovieByGenre(idGenre), HttpStatus.OK);

        List<MovieResponse> response = new ArrayList<>();

        service.findAllMovies().forEach( m -> {
            MovieResponse movieResponse = new MovieResponse(m);
            response.add(movieResponse);
        });

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<MovieDetailResponse> getMovieById(@PathVariable("id") int idMovie) {

        Movie savedMovie = service.findMovieById(idMovie);
        List<Character> characterInMovieList = service.findCharactersInMovieByIdMovie(idMovie);

        MovieDetailResponse response = new MovieDetailResponse(savedMovie);

        characterInMovieList.forEach( character -> {
            response.getCharacters().add(character.getName());
        });


        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/genres/{idGenre}/movies")
    public ResponseEntity<?> postMovie(
            @PathVariable("idGenre") Integer idGenre,
            @RequestParam String data,
            @RequestParam(required = false) MultipartFile imageFile) {


        ObjectMapper mapper = new ObjectMapper();

        try {
            Movie movie = mapper.readValue(data, Movie.class);

            String dateFormated = LocalDate.parse(movie.getCreationDate()).toString();
            movie.setCreationDate(dateFormated);

            Genre genre = genreService.findGenreById(idGenre);
            movie.setGenre( genre );


            if (imageFile != null && !imageFile.isEmpty()) {

                byte[] bytes = imageFile.getBytes();
                Path path = Paths.get(PATH_IMAGES + imageFile.getOriginalFilename());
                Files.write(path, bytes);
                movie.setImage(path.toString());

            } else {
                movie.setImage("no image");
            }

            Movie savedMovie = service.saveMovie(movie);
            System.out.println(movie);
            System.out.println(savedMovie);

            return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/movies/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable("id") int idMovie) {

        service.deleteMovie(idMovie);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
