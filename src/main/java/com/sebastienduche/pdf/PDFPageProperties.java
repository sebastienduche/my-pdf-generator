package com.sebastienduche.pdf;

import org.apache.pdfbox.pdmodel.font.PDFont;

public class PDFPageProperties {

  private float marginTop;
  private float marginBottom;
  private float marginLeft;
  private float marginRight;
  private PDFont font;
  private int fontSize;
  private float positionFirstLine;

  public PDFPageProperties(PDFont font, int fontSize) {
    this.font = font;
    this.fontSize = fontSize;
    marginTop = marginBottom = marginLeft = marginRight = 20;
    positionFirstLine = 40;
  }

  public PDFPageProperties(float marginTop, float marginBottom, float marginLeft, float marginRight, PDFont font, int fontSize, int positionFirstLine) {
    this.marginTop = marginTop;
    this.marginBottom = marginBottom;
    this.marginLeft = marginLeft;
    this.marginRight = marginRight;
    this.font = font;
    this.fontSize = fontSize;
    this.positionFirstLine = positionFirstLine;
  }

  float getMarginTop() {
    return marginTop;
  }

  public void setMarginTop(float marginTop) {
    this.marginTop = marginTop;
  }

  float getMarginBottom() {
    return marginBottom;
  }

  public void setMarginBottom(float marginBottom) {
    this.marginBottom = marginBottom;
  }

  float getMarginLeft() {
    return marginLeft;
  }

  public void setMarginLeft(float marginLeft) {
    this.marginLeft = marginLeft;
  }

  float getMarginRight() {
    return marginRight;
  }

  public void setMarginRight(float marginRight) {
    this.marginRight = marginRight;
  }

  public PDFont getFont() {
    return font;
  }

  public void setFont(PDFont font) {
    this.font = font;
  }

  int getFontSize() {
    return fontSize;
  }

  public void setFontSize(int fontSize) {
    this.fontSize = fontSize;
  }

  float getPositionFirstLine() {
    return positionFirstLine;
  }

  public void setPositionFirstLine(float positionFirstLine) {
    this.positionFirstLine = positionFirstLine;
  }
}
