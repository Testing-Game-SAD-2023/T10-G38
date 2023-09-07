package org.sad.classUTrepository.service;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.List;

import org.sad.classUTrepository.dto.ClassUT_DTO;
import org.sad.classUTrepository.entity.Admin;
import org.sad.classUTrepository.entity.ClassUT;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ClassUTService {
	
	String save(Admin admin, int complexity, MultipartFile classUT) throws IOException, InvalidPathException;
	List<ClassUT_DTO> getAll();
	ClassUT update(int id, int newComplexity);
	void delete(int id);
	List<ClassUT_DTO> getClassesbyAdmin(String admin_surname);
	List<ClassUT_DTO> getClassesbyComplexity(int complexity);
	Resource getClassUTasResource(String fileName) throws org.sad.classUTrepository.exception.ClassNotFoundException;
}
