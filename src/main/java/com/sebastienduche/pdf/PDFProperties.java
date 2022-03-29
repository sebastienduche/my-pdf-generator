package com.sebastienduche.pdf;

import java.util.LinkedList;

public class PDFProperties {

    private final LinkedList<PDFColumn> columns = new LinkedList<>();
    private String title;
    private int titleFontSize;
    private int defaultFontSize;
    private boolean border;
    private boolean boldTitle;
    private int marginBeforeTitle;

    public PDFProperties() {
        title = "";
        defaultFontSize = 10;
        border = true;
        boldTitle = false;
        marginBeforeTitle = 10;
    }

    public PDFProperties(String title) {
        this.title = title;
        defaultFontSize = 10;
        titleFontSize = 10;
        border = true;
        boldTitle = false;
        marginBeforeTitle = 10;
    }

    public PDFProperties(String title, int titleFontSize, int defaultFontSize, boolean border, boolean boldTitle, int marginBeforeTitle) {
        this.title = title;
        this.titleFontSize = titleFontSize;
        this.defaultFontSize = defaultFontSize;
        this.border = border;
        this.boldTitle = boldTitle;
        this.marginBeforeTitle = marginBeforeTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTitleFontSize() {
        return titleFontSize;
    }

    public void setTitleFontSize(int titleFontSize) {
        this.titleFontSize = titleFontSize;
    }

    public int getDefaultFontSize() {
        return defaultFontSize;
    }

    public void setDefaultFontSize(int defaultFontSize) {
        this.defaultFontSize = defaultFontSize;
    }

    public boolean isBorder() {
        return border;
    }

    public void setBorder(boolean border) {
        this.border = border;
    }

    public void addColumn(String field, int size, String title) {
        columns.add(new PDFColumn(field, size, title));
    }

    public void addColumn(int width, String title) {
        columns.add(new PDFColumn(null, width, title));
    }

    public LinkedList<PDFColumn> getColumns() {
        return columns;
    }

    float getColumnWidth(int i) {
        return columns.get(i).getWidth();
    }

    public String getColumnTitle(int i) {
        return columns.get(i).getTitle();
    }

    public boolean isBoldTitle() {
        return boldTitle;
    }

    private void setBoldTitle(boolean boldTitle) {
        this.boldTitle = boldTitle;
    }

    float getTotalColumnWidth() {
        int val = 0;
        for (PDFColumn c : columns) {
            val += c.getWidth();
        }
        return val;
    }

    PDFRow getPDFHeader() {
        PDFRow row = new PDFRow();
        for (PDFColumn column : columns) {
            row.addCell(column.getTitle());
        }
        return row;
    }

    public int getMarginBeforeTitle() {
        return marginBeforeTitle;
    }

    public PDFProperties setMarginBeforeTitle(int marginBeforeTitle) {
        this.marginBeforeTitle = marginBeforeTitle;
        return this;
    }
}
