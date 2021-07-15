package com.jdyapura.api.disney.services;

import com.jdyapura.api.disney.entities.Character;
import com.jdyapura.api.disney.entities.CharacterMovie;
import com.jdyapura.api.disney.entities.Movie;
import com.jdyapura.api.disney.repositories.CharacterMovieRepository;
import com.jdyapura.api.disney.repositories.CharacterRepository;
import com.jdyapura.api.disney.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CharacterService {

    @Autowired
    private CharacterRepository repository;

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private CharacterMovieRepository characterMovieRepository;

    public List<Character> findAllCharacters() {
        return repository.findAll();
    }

    public Character findCharacterById(int idCharacter) {
        Optional<Character> result = repository.findById(idCharacter);
        if (!result.isPresent())
            return null;
        return result.get();
    }

    public Character saveCharacter(int idMovie, Character newCharacter) {

        Optional<Movie> savedMovie = movieRepository.findById(idMovie);

        if (!savedMovie.isPresent())
            return null;
        Character savedCharacter =  repository.save(newCharacter);

        CharacterMovie characterMovie = new CharacterMovie(savedMovie.get(), savedCharacter);
        characterMovieRepository.save(characterMovie);
        return savedCharacter;
    }

    public void deleteCharacter(int idCharater) {
        if (!repository.existsById(idCharater))
            return;

        repository.deleteById(idCharater);
    }

    public Character updateCharacter(int idCharacter, Character newCharacter) {
        Character savedCharacter = findCharacterById(idCharacter);
        if( savedCharacter == null)
            return null;
        return repository.save(newCharacter);
    }

    public List<Movie> findMoviesToCharacterByIdCharacter(int idCharacter) {

        Character character = repository.getById(idCharacter);

        List<CharacterMovie> characterMovieList = characterMovieRepository.findByCharacter(character);

        List<Movie> movieList = new ArrayList<>();

        characterMovieList.forEach(c -> {
            movieList.add(c.getMovie());
        });

        return movieList;
    }
}
