package com.jdyapura.api.disney.repositories;

import com.jdyapura.api.disney.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    Movie findByTitle(String title);

    @Query(value = "SELECT * FROM movie WHERE genre_id_genre=:idGenre",
    nativeQuery = true)
    List<Movie> findMoviesByIdGenre(@Param("idGenre") int idGenre);

}
