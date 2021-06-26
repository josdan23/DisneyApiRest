package com.jdyapura.api.disney.repositories;

import com.jdyapura.api.disney.models.CharacterInMovie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterInMovieRepository extends JpaRepository<CharacterInMovie, Integer> {
}
