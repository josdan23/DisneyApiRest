package com.jdyapura.api.disney.services;

import com.jdyapura.api.disney.entities.*;
import com.jdyapura.api.disney.entities.Character;
import com.jdyapura.api.disney.repositories.CharacterMovieRepository;
import com.jdyapura.api.disney.repositories.GenreRepository;
import com.jdyapura.api.disney.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.*;

@Service
public class MovieService {

    private MovieRepository movieRepository;
    private GenreRepository genreRepository;
    private CharacterMovieRepository characterMovieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository,
                        GenreRepository genreRepository,
                        CharacterMovieRepository characterMovieRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.characterMovieRepository = characterMovieRepository;
    }

    public List<Movie> findAllMovies() throws RuntimeException {
        movieRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
        List<Movie> moviesStoredList = movieRepository.findAll();
        if (moviesStoredList.size() <= 0)
            throw new RuntimeException("Not content");
        return movieRepository.findAll();
    }

    public Movie findMovieById(int idMovie) {
        if (idMovie < 1)
            throw new RuntimeException("Argumento id no valido");

        Optional<Movie> result = movieRepository.findById(idMovie);

        if (!result.isPresent())
            throw new RuntimeException("No content find");

        return result.get();
    }


    public Movie saveMovie(Movie newMovie) {

        Genre savedGenre = genreRepository.findByName(newMovie.getGenre().getName());
        if (savedGenre == null)
            throw new RuntimeException("Gender property was not provided");

        if (newMovie.getTitle().isBlank() && newMovie.getTitle().isEmpty() && newMovie.getTitle() != null)
            throw new RuntimeException("Title property was not provided");

        if (newMovie.getCalification() < 0 && newMovie.getCalification() > 5)
            throw new RuntimeException("Calification property out of range");

        if (newMovie.getType().isEmpty()
                && newMovie.getType().isBlank()
                && newMovie.getType() == null)
            throw new RuntimeException("Type property was not provided");


        if (newMovie.getImage().isEmpty()
                && newMovie.getImage().isBlank()
                && newMovie.getImage() == null)
            throw new RuntimeException("Image property was not provided");


        if (Arrays.stream(TypeMovie.values()).noneMatch(typeMovie ->
                typeMovie.toString().equals(newMovie.getType().toUpperCase())))
            throw new RuntimeException("Type properties was not found");


        newMovie.setType(newMovie.getType().toUpperCase());

        newMovie.setGenre(savedGenre);
        return movieRepository.save(newMovie);
    }

    public Movie updateMovie(int idMovie, Movie updatedMovie) {

        Genre savedGenre = genreRepository.findByName(updatedMovie.getGenre().getName());
        if (savedGenre == null)
            throw new RuntimeException("Genre not exist");

        if (Arrays.stream(TypeMovie.values()).noneMatch(typeMovie ->
                typeMovie.toString().equals(updatedMovie.getType().toUpperCase())))
            throw new RuntimeException("Type properties was not found");

        Movie savedMovie = findMovieById(idMovie);
        if(savedMovie == null)
            throw new RuntimeException(String.format("Movie with id=%d no exist", idMovie));

        if (updatedMovie.getImage() != null)
            savedMovie.setImage(updatedMovie.getImage());

        savedMovie.setTitle(updatedMovie.getTitle());
        savedMovie.setCreationDate(updatedMovie.getCreationDate());
        savedMovie.setCalification(updatedMovie.getCalification());
        savedMovie.setType(updatedMovie.getType().toUpperCase());
        savedMovie.setGenre(updatedMovie.getGenre());

        return movieRepository.save(savedMovie);
    }

    public void deleteMovie(int idMovie) {
        if (idMovie < 0 )
            throw new RuntimeException("Id negative");

        if (!movieRepository.existsById(idMovie))
            throw new RuntimeException("Movie whit id no exist");

        movieRepository.deleteById(idMovie);
    }

    public List<Character> findCharactersInMovieByIdMovie(int idMovie) {

        if (idMovie < 0)
            throw new RuntimeException("idMovie no validate");

        Movie movie = movieRepository.getById(idMovie);

        if(movie == null) {
            throw new RuntimeException(String.format("Movie with id=%d no exist", idMovie));
        }

        List<CharacterMovie> characterMovieList = characterMovieRepository.findByMovie(movie);

        if( characterMovieList == null)
            throw new RuntimeException(String.format("No exist characters in Movie", idMovie));

        List<Character> characterList = new ArrayList<>();

        characterMovieList.forEach( c -> {
            characterList.add(c.getCharacter());
        });

        return characterList;
    }

    public Movie findMovieByName(String title){

        return movieRepository.findByTitle(title);
    }

    public List<Movie> findMovieByGenre(Integer idGenre) {

        return movieRepository.findMoviesByIdGenre(idGenre);
    }

}
