package org.sad.classUTrepository.service;

import org.sad.classUTrepository.entity.Test;
import org.sad.classUTrepository.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

	@Autowired TestRepository testRepository;
	
	@Override
	public void save(Test item) {
		testRepository.save(item);
	}

	@Override
	public Test getTestbyId(int id) {
		return testRepository.findById(id).get();
	}

	@Override
	public Test update(int id, String newName, String newPath, String newLevel) {
		Test toUpdate = testRepository.findById(id).get();
		toUpdate.setName(newName);
		toUpdate.setPath(newPath);
		toUpdate.setLevel(newLevel);
		return testRepository.save(toUpdate);
	}

	@Override
	public void delete(int id) {
		testRepository.deleteById(id);
	}

}
