package com.jdyapura.api.disney.services;

import com.jdyapura.api.disney.models.Character;
import com.jdyapura.api.disney.models.CharacterInMovie;
import com.jdyapura.api.disney.models.Movie;
import com.jdyapura.api.disney.repositories.CharacterInMovieRepository;
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
    private CharacterInMovieRepository characterInMovieRepository;

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

        Movie savedMovie = movieRepository.getById(idMovie);
        Character savedCharacter =  repository.save(newCharacter);

        CharacterInMovie characterInMovie = new CharacterInMovie(savedMovie, savedCharacter);
        characterInMovieRepository.save(characterInMovie);
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

        List<CharacterInMovie> characterInMovieList = characterInMovieRepository.findByCharacter(character);

        List<Movie> movieList = new ArrayList<>();

        characterInMovieList.forEach(c -> {
            movieList.add(c.getMovie());
        });

        return movieList;
    }
}
