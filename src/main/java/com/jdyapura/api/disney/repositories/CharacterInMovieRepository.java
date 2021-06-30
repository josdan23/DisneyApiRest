package com.jdyapura.api.disney.repositories;

import com.jdyapura.api.disney.entities.Character;
import com.jdyapura.api.disney.entities.CharacterMovie;
import com.jdyapura.api.disney.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterInMovieRepository extends JpaRepository<CharacterMovie, Integer> {


    List<CharacterMovie> findByCharacter(Character character);

    List<CharacterMovie> findByMovie(Movie movie);
}
