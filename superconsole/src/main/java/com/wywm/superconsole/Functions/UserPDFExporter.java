package com.wywm.superconsole.Functions;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//  Export to PDF Function
public class UserPDFExporter {

    private List<Troops> listTroops;

    // Data extracted from listTroops
    public UserPDFExporter(List<Troops> listTroops) {
        this.listTroops = listTroops;
    }

    // Table Format
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.WHITE);
        cell.setPadding(2);

        com.itextpdf.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(BaseColor.BLACK);

        cell.setPhrase(new Phrase("User ID", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Full Name", font));
        table.addCell(cell);

    }

    // Write Table Function
    private void writeTableData(PdfPTable table) {
        for (Troops Troops : listTroops) {
            table.addCell(String.valueOf(Troops.getId()));
            table.addCell(Troops.getFullName());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(BaseColor.WHITE);

        Paragraph p = new Paragraph("List of Soldiers Deployed", font);
        p.setAlignment(Element.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] { 3.0f, 3.0f });
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }

}
