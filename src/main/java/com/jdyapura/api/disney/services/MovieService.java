package com.jdyapura.api.disney.services;

import com.github.javafaker.Faker;
import com.jdyapura.api.disney.models.Genre;
import com.jdyapura.api.disney.models.Movie;
import com.jdyapura.api.disney.repositories.GenreRepository;
import com.jdyapura.api.disney.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    public List<Movie> findAllMovies() {
        return movieRepository.findAll();
    }

    public Movie findMovieById(int idMovie) {
        Optional<Movie> result = movieRepository.findById(idMovie);
        if (!result.isPresent())
            return null;
        return result.get();
    }

    public Movie saveMovie(Movie newMovie) {
        //TODO: validar si no existe

        Genre savedGenre = genreRepository.save(newMovie.getGenre());
        newMovie.setGenre(savedGenre);
        return movieRepository.save(newMovie);
    }

    public Movie updateMovie(int idMovie, Movie updatedMovie) {
        Movie savedMovie = findMovieById(idMovie);
        if(savedMovie == null)
            return null;

        return movieRepository.save(savedMovie);
    }

    public void deleteMovie(int idMovie) {
        if (!movieRepository.existsById(idMovie))
            return;

        movieRepository.deleteById(idMovie);
    }
}
