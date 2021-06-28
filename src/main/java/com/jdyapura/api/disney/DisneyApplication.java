package com.jdyapura.api.disney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DisneyApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DisneyApplication.class, args);




		FakerService fakerService = context.getBean(FakerService.class);
		fakerService.inserFakeDataDb();

	}

}
