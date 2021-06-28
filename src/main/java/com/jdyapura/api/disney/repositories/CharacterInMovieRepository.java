package com.jdyapura.api.disney.repositories;

import com.jdyapura.api.disney.models.Character;
import com.jdyapura.api.disney.models.CharacterInMovie;
import com.jdyapura.api.disney.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterInMovieRepository extends JpaRepository<CharacterInMovie, Integer> {


    List<CharacterInMovie> findByCharacter(Character character);

    List<CharacterInMovie> findByMovie(Movie movie);
}
