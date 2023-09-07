package com.project.ProgettoSad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class ProgettoSadApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgettoSadApplication.class, args);
	}

}
