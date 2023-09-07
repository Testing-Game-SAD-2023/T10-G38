package org.sad.classUTrepository.repository;

import org.sad.classUTrepository.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Integer> {
	
}
