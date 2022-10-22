package com.aakash.employee.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aakash.employee.entity.Employee;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.lowagie.text.StandardFonts;

public class GeneratePdfReport {
	private static final Logger LOG = LoggerFactory.getLogger(GeneratePdfReport.class);
	
	public static ByteArrayInputStream empReport(List<Employee> employee) {
		Document document = new Document();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		try {
			PdfPTable table = new PdfPTable(4);
			table.setWidthPercentage(100);
			table.setWidths(new int[] {1, 3, 3, 2});
			
			Font fonthead = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			
			PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("ID", fonthead));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Name", fonthead));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("City", fonthead));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("Salary", fonthead));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            for (Employee emp : employee) {

                PdfPCell cell;

                cell = new PdfPCell(new Phrase(emp.getId().toString()));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(emp.getName()));
                cell.setPaddingLeft(5);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(emp.getCity())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(5);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(String.valueOf(emp.getSalary().toString())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(5);
                table.addCell(cell);
            }
            
            

            PdfWriter.getInstance(document, outputStream);
            document.open();
            document.add(table);
            document.addTitle("Employee Data");
            document.addHeader("Info", "Employee");
            document.close();
		} catch (Exception e) {
			LOG.error("Error Occured: {0}", e);
		}
		return new ByteArrayInputStream(outputStream.toByteArray());
		
	}
}
