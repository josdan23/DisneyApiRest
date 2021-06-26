package com.jdyapura.api.disney.services;

import com.jdyapura.api.disney.models.Movie;
import com.jdyapura.api.disney.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    public List<Movie> findAllMovies() {
        return repository.findAll();
    }

    public Movie findMovieById(int idMovie) {
        Optional<Movie> result = repository.findById(idMovie);
        if (!result.isPresent())
            return null;
        return result.get();
    }

    public Movie saveMovie(Movie newMovie) {
        //TODO: validar si no existe
        return repository.save(newMovie);
    }

    public Movie updateMovie(int idMovie, Movie updatedMovie) {
        Movie savedMovie = findMovieById(idMovie);
        if(savedMovie == null)
            return null;

        return repository.save(savedMovie);
    }

    public void deleteMovie(int idMovie) {
        if (!repository.existsById(idMovie))
            return;

        repository.deleteById(idMovie);
    }
}
