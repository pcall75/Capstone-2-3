package com.wywm.superconsole.Functions;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class XmlGrab {

    /*
     * Reads data from an XML file and copies the data to a List (List<Troops>
     * TroopList)
     */
    public static List<Troops> getTroops() {
        List<Troops> troopList = new LinkedList<>();
        try {
            // File path to the XML file.
            Path filePath = Paths.get("/Users/patrick/Documents/GitHub/Capstone-2-3/superconsole/src/dataset.xml");
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
                    troopList.add(newTroop);
                }
            } else {
                System.out.println("File not found");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // Returns the TroopList with data from the XML file.
        return troopList;
    }
}
