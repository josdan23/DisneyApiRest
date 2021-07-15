package com.jdyapura.api.disney;

import com.github.javafaker.Faker;
import com.jdyapura.api.disney.entities.Character;
import com.jdyapura.api.disney.entities.Genre;
import com.jdyapura.api.disney.entities.Movie;
import com.jdyapura.api.disney.services.CharacterService;
import com.jdyapura.api.disney.services.GenreService;
import com.jdyapura.api.disney.services.MovieService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class DisneyApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DisneyApplication.class, args);

		GenreService genreService = context.getBean(GenreService.class);
		MovieService movieService = context.getBean(MovieService.class);
		CharacterService characterService = context.getBean(CharacterService.class);

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

			Random random = new Random();
			int randomIndex =random.nextInt(listGenre.size());

			movieService.saveMovie(new Movie(
					faker.book().title(),
					LocalDate.now().toString(),
					3,
					"no image",
					"movie",
					genreService.findGenreById(listGenre.get(randomIndex).getIdGenre())));
		}


		List<Movie> movieList = movieService.findAllMovies();

		for (int i = 0; i < 20; i++) {

			Random random = new Random();
			int randomIndex = random.nextInt(movieList.size() - 1);

			characterService.saveCharacter(randomIndex, new Character(
					faker.name().firstName(),
					faker.number().numberBetween(10, 60),
					faker.number().randomNumber(),
					"esta es un historia",
					"No image"
			));

		}
	}

}
