package com.wywm.superconsole.controller;

import com.itextpdf.text.DocumentException;
import com.wywm.superconsole.Functions.Troops;
import com.wywm.superconsole.Functions.UserPDFExporter;
import com.wywm.superconsole.user.User;
import com.wywm.superconsole.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.wywm.superconsole.Functions.ListAsc.getNumAsc;
import static com.wywm.superconsole.Functions.ListDesc.getNumDes;

//Controller Layer/AP Layer - This layers job is simply to receive and handle HTTP (GET, POST, PUT, DELETE) requests sent from clients.
@Controller
public class AppController {

	@Autowired
	private UserRepository userRepo;

	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}

	@GetMapping("/index")
	public String logoutPage() {
		return "index";
	}

	@GetMapping("/menu")
	public String menuPage() {
		return "menu";
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());

		return "signup_form";
	}

	// Encodes password
	@PostMapping("/process_register")
	public String processRegister(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		userRepo.save(user);

		return "register_success";
	}

	// Reads the user database
	@GetMapping("/users")
	public String listUsers(Model model) {
		List<User> listUsers = userRepo.findAll();
		model.addAttribute("listUsers", listUsers);

		return "users";
	}

	// Data sort Functions
	@GetMapping(value = "/numasc")
	public String listAsc(Model model) {
		List<Troops> ascList = getNumAsc();
		model.addAttribute("AscList", ascList);
		return "numasc";
	}

	@GetMapping(value = "/numdesc")
	public String listDesc(Model model) {
		List<Troops> descList = getNumDes();
		model.addAttribute("DescList", descList);
		return "numdesc";
	}

	// PDF Export Functions
	@GetMapping("/pdfasc")
	public void ascExportToPDF(HttpServletResponse ascResponse) throws DocumentException, IOException {
		ascResponse.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=Ascending list of soldiers deployed_" + currentDateTime + ".pdf";
		ascResponse.setHeader(headerKey, headerValue);

		List<Troops> listTroops = getNumAsc();

		UserPDFExporter exporter = new UserPDFExporter(listTroops);
		exporter.export(ascResponse);

	}

	@GetMapping("/pdfdesc")
	public void descExportToPDF(HttpServletResponse descResponse) throws DocumentException, IOException {
		descResponse.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=Descending list of soldiers deployed_" + currentDateTime + ".pdf";
		descResponse.setHeader(headerKey, headerValue);

		List<Troops> listTroops = getNumDes();

		UserPDFExporter exporter = new UserPDFExporter(listTroops);
		exporter.export(descResponse);

	}

	// Error 403 return
	@GetMapping("/403")
	public String error403() {
		return "403";
	}

}
