package com.jdyapura.api.disney;

import com.github.javafaker.Faker;
import com.jdyapura.api.disney.entities.Genre;
import com.jdyapura.api.disney.entities.Movie;
import com.jdyapura.api.disney.services.GenreService;
import com.jdyapura.api.disney.services.MovieService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class DisneyApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DisneyApplication.class, args);

		GenreService genreService = context.getBean(GenreService.class);
		MovieService movieService = context.getBean(MovieService.class);

		Faker faker = new Faker();
		for (int i = 0 ; i < 10 ; i++ ) {
			try {
				genreService.saveGenre(new Genre(faker.book().genre(), "no image"));

			} catch (RuntimeException e) {

			}
			finally {
				continue;
			}
		}

		List<Genre> listGenre = genreService.findAllGenres();

		for (int i = 0; i < 10; i++ ) {

			int randomIndex = (int) (Math.random() * listGenre.size());

			movieService.saveMovie(new Movie(
					faker.book().title(),
					LocalDate.now().toString(),
					3,
					"no image",
					"movie",
					genreService.findGenreById(listGenre.get(randomIndex).getIdGenre())));
		}
	}

}
