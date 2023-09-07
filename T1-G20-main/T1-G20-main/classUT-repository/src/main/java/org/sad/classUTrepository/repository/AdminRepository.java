package org.sad.classUTrepository.repository;

import org.sad.classUTrepository.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
	
	Admin findByEmail(String email);
	
}
