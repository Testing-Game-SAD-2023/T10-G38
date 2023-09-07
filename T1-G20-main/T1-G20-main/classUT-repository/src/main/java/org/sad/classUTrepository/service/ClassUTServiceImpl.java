package org.sad.classUTrepository.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.management.modelmbean.ModelMBeanAttributeInfo;

import org.modelmapper.ModelMapper;
import org.sad.classUTrepository.config.FileUploadConfiguration;
import org.sad.classUTrepository.dto.ClassUT_DTO;
import org.sad.classUTrepository.entity.Admin;
import org.sad.classUTrepository.entity.ClassUT;
import org.sad.classUTrepository.repository.ClassUTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClassUTServiceImpl implements ClassUTService{

	@Autowired
	ClassUTRepository classRepository;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	FileUploadConfiguration fileUploadConfig;
	
	@Override
	public String save(Admin admin, int complexity, MultipartFile classUT) throws IOException, InvalidPathException {
		
		String classFolder = fileUploadConfig.getUploadDir() + classUT.getOriginalFilename().replace(".java", "") + "//";
		String filePath = classFolder + classUT.getOriginalFilename();
		try {
			/*if (filePath.contains("..")) {
				throw new FileStorageException("Invalid path!"+filePath);
			}*/
			Path directory = Paths.get(classFolder);
			Files.createDirectory(directory);
			classUT.transferTo(new File(filePath));
			
			ClassUT toSave = new ClassUT();
			toSave.setName(classUT.getOriginalFilename());
			toSave.setType(classUT.getContentType());
			toSave.setSize(classUT.getSize());
			if(filePath.length() > ClassUT.LOCATION_LENGHT)
				throw new InvalidPathException(filePath, "The path exceeds the maximum location lenght", 2);
			else
				toSave.setLocation(filePath);
			toSave.setComplexity(complexity);
			toSave.setAdded(new Date()); 
			toSave.setLastupdate(toSave.getAdded());
			toSave.setAdmin(admin);
			classRepository.save(toSave);			
		
		return filePath;
		}catch(IOException e) {
			throw new IOException("Can't store file ",e);
		}
		catch(InvalidPathException e) {
			throw new InvalidPathException(filePath, "Invalid path!", 1);
		}
	}

	@Override
	public List<ClassUT_DTO> getAll() {
		
		List<ClassUT> classes = classRepository.findAll();
		List<ClassUT_DTO> classesDTO = new ArrayList<ClassUT_DTO>(classes.size());
		
		classes.forEach(item -> {
			classesDTO.add(modelMapper.map(item, ClassUT_DTO.class));
		});
		return classesDTO;
	}

	@Override
	public ClassUT update(int id, int newComplexity) {
		
		ClassUT toUpdate = classRepository.findById(id).get();
		toUpdate.setComplexity(newComplexity);
		return classRepository.save(toUpdate);
	}

	@Override
	public void delete(int id) {
		classRepository.deleteById(id);
	}

	@Override
	public List<ClassUT_DTO> getClassesbyAdmin(String admin_surname) {
		
		List<ClassUT> classes = classRepository.findAllClassUTByAdmin_Surname(admin_surname);
		List<ClassUT_DTO> classesDTO = new ArrayList<ClassUT_DTO>(classes.size());
		
		classes.forEach(item -> {
			classesDTO.add(modelMapper.map(item, ClassUT_DTO.class));
		});
		return classesDTO;
	}

	@Override
	public List<ClassUT_DTO> getClassesbyComplexity(int complexity) {
		
		List<ClassUT> classes = classRepository.findAllClassUTByComplexity(complexity);
		List<ClassUT_DTO> classesDTO = new ArrayList<ClassUT_DTO>(classes.size());
		
		classes.forEach(item -> {
			classesDTO.add(modelMapper.map(item, ClassUT_DTO.class));
		});
		return classesDTO;
	}

	@Override
	public Resource getClassUTasResource(String fileName) throws org.sad.classUTrepository.exception.ClassNotFoundException {
		try {
			ClassUT toDownload = classRepository.findByname(fileName);
			String classPath = toDownload.getLocation();
			Resource resourceClass = new UrlResource(new File(classPath).toURI());
			if (resourceClass.exists()) {
				return resourceClass;
			}else {
				throw new org.sad.classUTrepository.exception.ClassNotFoundException("Class file not found "+fileName);
			}
		}catch(MalformedURLException e) {
			throw new org.sad.classUTrepository.exception.ClassNotFoundException("Class file not found "+fileName,e);
		}
	}
}
