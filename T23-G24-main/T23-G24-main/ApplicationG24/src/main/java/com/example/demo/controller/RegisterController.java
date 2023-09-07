package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@Controller
public class RegisterController
{
	@Autowired
	private UserService userService;

	/*public RegisterController(UserService userService)
	{
		this.userService = userService;
	}*/

	@GetMapping("/register")
	public String showRegistrationForm(@ModelAttribute("user") UserDto userDto/*, Model model*/)
	{
		return "register";
	}

	@PostMapping("/register/save")
	public String registration(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result/*, Model model*/)
	{
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
	}
}