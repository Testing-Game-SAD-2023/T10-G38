package org.sad.classUTrepository.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;

@Configuration
@Getter
public class FileUploadConfiguration {
	
	@Value("${file.upload-dir}")
	private String uploadDir;

}
