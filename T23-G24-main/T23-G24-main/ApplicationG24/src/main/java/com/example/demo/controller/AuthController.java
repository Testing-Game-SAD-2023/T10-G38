package com.example.demo.controller;

import java.util.List;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;

//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.security.core.userdetails.UserDetails;

//import com.example.demo.entity.User;
//import jakarta.validation.Valid;

@Controller
public class AuthController
{
	@Autowired
	private UserService userService;

	//Autowired, inserito al posto del costruttore.
	//Non inserendo autowired, c'è bisogno del costruttore altrimenti NON VA!
	
	/*public AuthController(UserService userService)
	{
		this.userService = userService;
	}*/

	@GetMapping("/index")
	public String index()
	{
		return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/home")
	public String home(/*Model model*/) 
	{
		//Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//String username = ((UserDetails)principal).getUsername();
		//User user = userService.findUserByEmail(username);
		//model.addAttribute("user", user);

		if((SecurityContextHolder.getContext().getAuthentication().getAuthorities()).contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
			return "redirect:/users";
		else
			return "redirect:/home/user";
	}

	@GetMapping("/home/user")
	public String homeUser(Model model)
	{
		return "home"; 
	}

	@GetMapping("/users")
	public String listRegisteredUsers(Model model)
	{
		List<UserDto> users = userService.findAllUsers();
		model.addAttribute("users", users);
		return "users";
	}
	
	//@GetMapping("/register")
	//public String showRegistrationForm(@ModelAttribute("user") UserDto userDto/*, Model model*/)
	/*{
		return "register";
	}*/ 

	//@PostMapping("/register/save")
	//public String registration(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result/*, Model model*/)
	/*{
		User existingUser = userService.findUserByEmail(userDto.getEmail());

		if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty())
		{
			result.rejectValue("email", null, "C'è già un account registrato con la stessa email");
		}

		if(result.hasErrors())
		{
			return "/register";
		}

		userService.saveUser(userDto);
		return "redirect:/login?registerSuccess";
	}*/
}