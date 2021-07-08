package com.jdyapura.api.disney.repositories;

import com.jdyapura.api.disney.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    Movie findByTitle(String title);

    //@Query("SELECT m FROM movie WHERE movie.genre.id_genre=idGenre")
    //List<Movie> findMoviesByIdGenre(int idGenre);

}
