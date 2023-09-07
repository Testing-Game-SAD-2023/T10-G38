package org.sad.classUTrepository.controller;

import jakarta.validation.Valid;
import org.sad.classUTrepository.entity.Admin;
import org.sad.classUTrepository.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



@Controller
public class AuthController {
	
	@Autowired AdminService adminService;

    // handler method to handle home page request
    @GetMapping("/index")
    public String home(){
        return "index";
    }

    
    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        Admin user = new Admin();
        model.addAttribute("user", user);
        return "register";
    }
    
    
    // handler method to handle user registration form submit request
    @PostMapping("register/save")
    public String registration(@Valid @ModelAttribute("user") Admin adminDto,
                               BindingResult result,
                               Model model){
        Admin existingAdmin = adminService.findByEmail(adminDto.getEmail());

        if(existingAdmin != null && existingAdmin.getEmail() != null && !existingAdmin.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", adminDto);
            return "register";
        }

        adminService.save(adminDto);
        //return "redirect:/register?success";
        return "redirect:/register?success";
    }
    
    
    // handler method to handle login request
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    
    
    @GetMapping("/upload")
    public String upload(){
        return "upload";
    }
    
    
    
}
