package com.jdyapura.api.disney;

import com.github.javafaker.Faker;
import com.jdyapura.api.disney.entities.Character;
import com.jdyapura.api.disney.entities.Genre;
import com.jdyapura.api.disney.entities.Movie;
import com.jdyapura.api.disney.repositories.GenreRepository;
import com.jdyapura.api.disney.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class FakerService {

    private Faker faker = new Faker();

    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private MovieRepository movieRepository;

    public void inserFakeDataDb(){

        //insert genres
        createListGenreFake().forEach( genre ->
        {
            genreRepository.save(genre);
        });

        createListMovieFake().forEach( movie -> {
            movieRepository.save(movie);
        });
    }

    private List<Genre> createListGenreFake(){

        List<Genre> fakeListGenres = new ArrayList<>();
        for (int i = 0; i < 10 ; i++) {
            fakeListGenres.add(new Genre(
                    faker.book().genre(),
                    "no Image"
            ));
        }
        return fakeListGenres;
    }

    private List<Movie> createListMovieFake(){

        List<Genre> listSavedGenres = genreRepository.findAll();
        Random randon = new Random();


        List<Movie> fakeListMovies = new ArrayList<>();

        /*for (int i = 0; i < 100; i++) {
            Movie movie = new Movie(
                    faker.book().title(),
                    faker.date().birthday(),
                    faker.number().numberBetween(0,5),
                    "imagen",
                    listSavedGenres.get(randon.nextInt(listSavedGenres.size()-1))
            );
            fakeListMovies.add(movie);
        }*/

        return fakeListMovies;
    }

    private Character createFakeCharacter(){

        Character fakeCharacter = new Character(
                faker.book().author(),
                faker.number().numberBetween(20,80),
                faker.number().randomDouble(2, 0,90),
                faker.lorem().characters(),
                "No image");

        return fakeCharacter;
    }

    private List<Character> createFakeCharactersList(){
        List<Character> characterList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            characterList.add(createFakeCharacter());
        }

        return characterList;
    }
}
