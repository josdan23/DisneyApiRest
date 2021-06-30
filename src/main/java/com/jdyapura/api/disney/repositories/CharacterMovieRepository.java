package com.jdyapura.api.disney.repositories;

import com.jdyapura.api.disney.entities.Character;
import com.jdyapura.api.disney.entities.CharacterMovie;
import com.jdyapura.api.disney.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterMovieRepository extends JpaRepository<CharacterMovie, Integer> {


    List<CharacterMovie> findByCharacter(Character character);

    List<CharacterMovie> findByMovie(Movie movie);
}
