package com.sebastienduche.pdf;

import org.apache.pdfbox.pdmodel.font.PDFont;

import java.util.LinkedList;
import java.util.List;

public class PDFRow {

  private final LinkedList<String> columns = new LinkedList<>();
  private PDFont font;
  private int fontSize;

  public PDFRow() {
  }

  public void addCell(String value) {
    columns.add(value);
  }

  List<String> getCells() {
    return columns;
  }

  int getCellCount() {
    return columns.size();
  }

  public void setFont(PDFont font, int fontSize) {
    this.font = font;
    this.fontSize = fontSize;
  }

  public PDFont getFont() {
    return font;
  }

  int getFontSize() {
    return fontSize;
  }
}
