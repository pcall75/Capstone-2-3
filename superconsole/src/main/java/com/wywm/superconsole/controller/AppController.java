package com.wywm.superconsole.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import static com.wywm.superconsole.controller.user.User.*;

import java.util.List;

@Controller
public class AppController {

	@Autowired
	private com.wywm.superconsole.controller.user.UserRepository userRepo;

	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new com.wywm.superconsole.controller.user.User());

		return "signup_form";
	}

	@PostMapping("/process_register")
	public String processRegister(com.wywm.superconsole.controller.user.User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		userRepo.save(user);

		return "register_success";
	}

	@GetMapping("/users")
	public String listUsers(Model model) {
		List<com.wywm.superconsole.controller.user.User> listUsers = userRepo.findAll();
		model.addAttribute("listUsers", listUsers);

		return "users";
	}
}
