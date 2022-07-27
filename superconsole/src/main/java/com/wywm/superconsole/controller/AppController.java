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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Controller
public class AppController {

	@Autowired
	private UserRepository userRepo;

	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}

	@GetMapping("/index")
	public String logoutPage() {return "index";}

	@GetMapping("/menu")
	public String menuPage() {return "menu";}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());

		return "signup_form";
	}

	@PostMapping("/process_register")
	public String processRegister(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		userRepo.save(user);

		return "menu";
	}

	@GetMapping("/users")
	public String listUsers(Model model) {
		List<User> listUsers = userRepo.findAll();
		model.addAttribute("listUsers", listUsers);

		return "users";
	}
	// Reads data from an XML file and copys the data to a List (List<User>
	// userList).
	public  List<Troops> getTroops() {
		List<Troops> TroopList = new LinkedList<>();
		try {
			// File path to the XML file.
			Path filePath = Paths.get("C:\\Users\\amani\\OneDrive\\WYWM\\Java\\Capstone 1\\dataset.xml");
			File file = new File(String.valueOf(filePath.toAbsolutePath()));

			if (file.exists()) {
				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
				Document document = documentBuilder.parse(String.valueOf(filePath.toAbsolutePath()));
				// Reads the XML tagName of full_name and id.
				NodeList[] user = { document.getElementsByTagName("full_name"), document.getElementsByTagName("id") };

				for (int i = 0; i < user[0].getLength(); i++) {

					String fullName = user[0].item(i).getTextContent();
					int id = Integer.parseInt(user[1].item(i).getTextContent());
					Troops newTroop = new Troops(fullName, id);
					TroopList.add(newTroop);
				}
			} else {
				System.out.println("File not found");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// Returns the TroopList with data from the XML file.
		return TroopList;

	}
	@RequestMapping(value = "/numasc", method = RequestMethod.GET)
	public String listAsc(Model model) {
		List<Troops> ascList = getNumAsc();
		model.addAttribute("AscList", ascList);
		return "numasc";
	}


	public List<Troops> getNumAsc( ) {
		List<Troops> ascList = new LinkedList<Troops>();
		ascList.addAll(getTroops());
		ascList.sort((u1, u2) -> u1.getId() - u2.getId());
		return ascList;
	}

	@RequestMapping(value = "/numdesc", method = RequestMethod.GET)
	public String listDesc(Model model) {
		List<Troops> descList = getNumDes();
		model.addAttribute("DescList", descList);
		return "numdesc";
	}

	public  List<Troops> getNumDes() {
		List<Troops> desList = new LinkedList<Troops>();
		desList.addAll(getTroops());
		desList.sort((u1, u2) -> u2.getId() - u1.getId());
		return desList;
	}




	@GetMapping("/export/pdfasc")
	public void AscexportToPDF(HttpServletResponse Ascresponse) throws DocumentException, IOException {
		Ascresponse.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=Ascending list of soldiers deployed_" + currentDateTime + ".pdf";
		Ascresponse.setHeader(headerKey, headerValue);

		List<Troops> listTroops = getNumAsc();


	 UserPDFExporter exporter = new UserPDFExporter(listTroops);
		exporter.export(Ascresponse);

	}



	@GetMapping("/export/pdfdesc")
	public void DescexportToPDF(HttpServletResponse DescResponse) throws DocumentException, IOException {
		DescResponse.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=Descending list of soldiers deployed_" + currentDateTime + ".pdf";
		DescResponse.setHeader(headerKey, headerValue);

		List<Troops> listTroops = getNumDes();

		UserPDFExporter exporter = new UserPDFExporter(listTroops);
		exporter.export(DescResponse);

	}

/*

	public void PdfAsc(HttpServletResponse response) throws DocumentException, IOException {

		try {

			// Create Document instance.
			com.itextpdf.text.Document document = new com.itextpdf.text.Document();

			// Create OutputStream instance.
			OutputStream outputStream = new FileOutputStream(
					new File("C:\\Users\\amani\\OneDrive\\WYWM\\Java\\Capstone 1\\asc.pdf"));


			// Create PDFWriter instance.
			PdfWriter.getInstance(document, outputStream);

			// Open the document.
			document.open();

			// Create asc List
			com.itextpdf.text.List ascList = new com.itextpdf.text.List(com.itextpdf.text.List.ORDERED);
			ascList.add(new ListItem(String.valueOf((getNumAsc()))));

			// Add casdList to the pdf.
			document.add(ascList);

			// Close document and outputStream.
			document.close();
			outputStream.close();

			System.out.println("\n" + "\n" + "PDF created successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void pdfDesc() {

		try {
			// Create Document instance.
			com.itextpdf.text.Document document = new com.itextpdf.text.Document();

			// Create OutputStream instance.
			OutputStream outputStream = new FileOutputStream(
					new File("C:\\Users\\amani\\OneDrive\\WYWM\\Java\\Capstone 1\\desc.pdf"));

			// Create PDFWriter instance.
			PdfWriter.getInstance(document, outputStream);

			// Open the document.
			document.open();

			// Create reverseOrder list.
			com.itextpdf.text.List descList = new com.itextpdf.text.List(com.itextpdf.text.List.ORDERED);
			descList.add(new ListItem(String.valueOf((getNumDes()))));
			Collections.reverseOrder();

			// Add descList.
			document.add(descList);
			// document.add(unorderedList);

			// Close document and outputStream.
			document.close();
			outputStream.close();

			System.out.println("\n" + "\n" + "PDF created successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/




}
