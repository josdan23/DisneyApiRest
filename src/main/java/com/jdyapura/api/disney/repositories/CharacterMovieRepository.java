package com.jdyapura.api.disney.repositories;

import com.jdyapura.api.disney.entities.Character;
import com.jdyapura.api.disney.entities.CharacterMovie;
import com.jdyapura.api.disney.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CharacterMovieRepository extends JpaRepository<CharacterMovie, Integer> {


    List<CharacterMovie> findByCharacter(Character character);

    List<CharacterMovie> findByMovie(Movie movie);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM character_movie WHERE character_id=:idCharacter",
            nativeQuery = true)
    void deleteAllCharacterMovieWithIdCharacter(@Param("idCharacter") int idCharacter);
}
