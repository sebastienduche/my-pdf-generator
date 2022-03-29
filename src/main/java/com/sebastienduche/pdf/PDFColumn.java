package com.sebastienduche.pdf;

public class PDFColumn {

    private int width;
    private String title;
    private String field;

    PDFColumn(String field, int width, String title) {
        this.width = width;
        this.field = field;
        this.title  = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public String getField() {
        return field;
    }

    private void setField(String field) {
        this.field = field;
    }
}
