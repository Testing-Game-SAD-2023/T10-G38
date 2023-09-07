package org.sad.classUTrepository.service;

import org.sad.classUTrepository.entity.Test;

public interface TestService {

	void save(Test item);
	Test getTestbyId(int id);
	Test update(int id, String newName, String newPath, String newLevel);
	void delete(int id);
}
