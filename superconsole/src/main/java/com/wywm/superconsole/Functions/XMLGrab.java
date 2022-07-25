package com.wywm.superconsole.Functions;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/list")
public class XMLGrab {

    // Reads data from an XML file and copys the data to a List (List<User>
    // userList).

    @GetMapping("list")
    public static List<Troops> getTroops() {
        List<Troops> TroopList = new LinkedList<>();
        try {
            // File path to the XML file.
            Path filePath = Paths.get("/Users/patrick/Documents/GitHub/CapstoneOne/src/dataset.xml");
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

@GetMapping("NumAsc")
    public static List<Troops> getNumAsc() {
        List<Troops> ascList = new LinkedList<Troops>();
        ascList.addAll(getTroops());
        ascList.sort((u1, u2) -> u1.getId() - u2.getId());
        return ascList;
    }

    @GetMapping("NumDes")
    public static List<Troops> getNumDes() {
        List<Troops> desList = new LinkedList<Troops>();
        desList.addAll(getTroops());
        desList.sort((u1, u2) -> u2.getId() - u1.getId());

        return desList;
    }






}
