package com.jdyapura.api.disney;

import com.jdyapura.api.disney.services.GenreService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DisneyApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DisneyApplication.class, args);

		GenreService genreService = context.getBean(GenreService.class);

		try {
			System.out.println( genreService.findAllGenres());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

/*
		FakerService fakerService = context.getBean(FakerService.class);
		fakerService.inserFakeDataDb();*/

	}

}
