package com.sebastienduche.pdf;

import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

class PDFToolsTest {

  public static void main(String[] args) throws IOException {
    LinkedList<PDFRow> rows = new LinkedList<>();
    PDFRow row;
    for(int i = 0; i < 50; i++) {
      row = new PDFRow();
      for(int j = 0; j < 6; j++)
        row.addCell("Test" + i + " " + j);
      rows.add(row);
    }

    PDFPageProperties pageProperties = new PDFPageProperties(5, 5, 5, 5, PDType1Font.HELVETICA, 12, 40);
    PDFProperties properties = new PDFProperties("Title", 12, 10, true, true, 20);
    final PDFTools pdfTools = new PDFTools(properties, pageProperties, true);
    properties.addColumn(2, "col 1");
    properties.addColumn(2, "col 2");
    properties.addColumn(3, "col 3");
    properties.addColumn(3, "col 4");
    properties.addColumn(4, "col 5");
    properties.addColumn(3, "col 6");
    pdfTools.writeData(rows);
    pdfTools.save(new File("test.pdf"));

    // Simple example
    final PDFTools pdfTools1 = new PDFTools();
    pdfTools1.writeData(rows);
    pdfTools1.save(new File("test1.pdf"));

    // Simple example
    final PDFTools pdfTools2 = new PDFTools("Title");
    pdfTools2.writeData(rows);
    pdfTools2.save(new File("test2.pdf"));
  }
}
