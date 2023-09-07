package org.sad.classUTrepository.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.sad.classUTrepository.entity.Admin;
import org.sad.classUTrepository.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
	
	
	/*public AdminServiceImpl(AdminRepository adminRepository,
            
            PasswordEncoder passwordEncoder) {
			this.adminRepository = adminRepository;
			this.passwordEncoder = passwordEncoder;
}*/

	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	/*@Override
	public void save(Admin item) {
		adminRepository.save(item);
	}*/

	@Override
	public Admin getAdminbyId(int id) {
		return adminRepository.findById(id).get();
	}
	
	 @Override
	    public Admin findByEmail(String email) {
	        return adminRepository.findByEmail(email);
	    }
	 
	 @Override
	    public void save(Admin AdminDto) {
	        
	        // encrypt the password using spring security
	        AdminDto.setPsw(passwordEncoder.encode(AdminDto.getPsw()));
	        adminRepository.save(AdminDto);
	    }

	@Override
	public Admin update(int id, String newName, String newSurname, String newEmail, String newPsw) {
		
		Admin updateAdmin = adminRepository.findById(id).get();
		updateAdmin.setName(newName);
		updateAdmin.setSurname(newSurname);
		//updateAdmin.setUsername(newUser);
		updateAdmin.setEmail(newEmail);
		updateAdmin.setPsw(newPsw);
		return adminRepository.save(updateAdmin);
	}

	@Override
	public void delete(int id) {
		adminRepository.deleteById(id);
	}

}
