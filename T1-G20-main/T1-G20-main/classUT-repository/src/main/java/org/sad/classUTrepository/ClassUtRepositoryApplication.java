package org.sad.classUTrepository;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.sad.classUTrepository.dto.ClassUT_DTO;
import org.sad.classUTrepository.entity.Admin;
import org.sad.classUTrepository.entity.ClassUT;
import org.sad.classUTrepository.service.AdminService;
import org.sad.classUTrepository.service.ClassUTService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.deser.impl.JavaUtilCollectionsDeserializers;

@SpringBootApplication
public class ClassUtRepositoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClassUtRepositoryApplication.class, args);

	}
}