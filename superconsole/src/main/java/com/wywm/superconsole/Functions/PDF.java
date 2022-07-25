package com.example.SupervisorConsole.Functions;

import com.itextpdf.text.Document;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/pdflist")
public class PDF {

    @GetMapping("ExportToPDFDesc")
    public static void pdfDesc() {

        try {
            // Create Document instance.
            Document document = new Document();

            // Create OutputStream instance.
            OutputStream outputStream = new FileOutputStream(
                    new File("/Users/patrick/Documents/GitHub/CapstoneOne/src/desc.pdf"));

            // Create PDFWriter instance.
            PdfWriter.getInstance(document, outputStream);

            // Open the document.
            document.open();

            // Create reverseOrder list.
            List descList = new List(List.ORDERED);
            descList.add(new ListItem(String.valueOf((XMLGrab.getNumDes()))));
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

    @GetMapping("ExportToPDFAsc")
    public static void pdfAsc() {

        try {
            // Create Document instance.
            Document document = new Document();

            // Create OutputStream instance.
            OutputStream outputStream = new FileOutputStream(
                    new File("/Users/patrick/Documents/GitHub/CapstoneOne/src/asc.pdf"));

            // Create PDFWriter instance.
            PdfWriter.getInstance(document, outputStream);

            // Open the document.
            document.open();

            // Create asc List
            List ascList = new List(List.ORDERED);
            ascList.add(new ListItem(String.valueOf((XMLGrab.getNumAsc()))));

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


}
