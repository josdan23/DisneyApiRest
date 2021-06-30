package com.jdyapura.api.disney.services;

import com.jdyapura.api.disney.entities.Character;
import com.jdyapura.api.disney.entities.CharacterMovie;
import com.jdyapura.api.disney.entities.Genre;
import com.jdyapura.api.disney.entities.Movie;
import com.jdyapura.api.disney.repositories.CharacterInMovieRepository;
import com.jdyapura.api.disney.repositories.GenreRepository;
import com.jdyapura.api.disney.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private CharacterInMovieRepository characterInMovieRepository;

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

        Genre savedGenre = genreRepository.findByName(newMovie.getGenre().getName());
        if (savedGenre == null)
            return null;
        newMovie.setGenre(savedGenre);
        return movieRepository.save(newMovie);
    }

    public Movie updateMovie(int idMovie, Movie updatedMovie) {

        Genre savedGenre = genreRepository.findByName(updatedMovie.getGenre().getName());
        if (savedGenre == null)
            return null;

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

    public List<Character> findCharactersInMovieByIdMovie(int idMovie) {
        Movie movie = movieRepository.getById(idMovie);

        List<CharacterMovie> characterMovieList = characterInMovieRepository.findByMovie(movie);

        List<Character> characterList = new ArrayList<>();

        characterMovieList.forEach(c -> {
            characterList.add(c.getCharacter());
        });

        return characterList;
    }
}
