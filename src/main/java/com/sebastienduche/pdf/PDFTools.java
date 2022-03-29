package com.sebastienduche.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PDFTools {

    private static final float ROW_HEIGHT = 20f;
    private static final float CELL_MARGIN = 5f;
    private static final float MAX_WIDTH = 20f;
    private final PDDocument document;
    private final PDFProperties properties;
    private final PDFPageProperties pageProperties;
    private final boolean withTableHeader;
    private PDPage page;
    private PDPageContentStream content;

    public PDFTools() throws IOException {
        this.properties = new PDFProperties();
        this.pageProperties = new PDFPageProperties(PDType1Font.HELVETICA, properties.getDefaultFontSize());
        this.withTableHeader = false;
        document = new PDDocument();
        page = new PDPage();
        document.addPage(page);
        content = new PDPageContentStream(document, page);
    }

    public PDFTools(String title) throws IOException {
        this.properties = new PDFProperties(title);
        this.pageProperties = new PDFPageProperties(PDType1Font.HELVETICA, properties.getDefaultFontSize());
        this.withTableHeader = false;
        document = new PDDocument();
        page = new PDPage();
        document.addPage(page);
        content = new PDPageContentStream(document, page);
    }

    public PDFTools(String title, boolean withTableHeader) throws IOException {
        this.properties = new PDFProperties(title);
        this.pageProperties = new PDFPageProperties(PDType1Font.HELVETICA, properties.getDefaultFontSize());
        this.withTableHeader = withTableHeader;
        document = new PDDocument();
        page = new PDPage();
        document.addPage(page);
        content = new PDPageContentStream(document, page);
    }

    public PDFTools(PDFProperties properties, boolean withTableHeader) throws IOException {
        this.properties = properties;
        this.pageProperties = new PDFPageProperties(PDType1Font.HELVETICA, properties.getDefaultFontSize());
        this.withTableHeader = withTableHeader;
        document = new PDDocument();
        page = new PDPage();
        document.addPage(page);
        content = new PDPageContentStream(document, page);
    }

    public PDFTools(PDFProperties properties, PDFPageProperties pageProperties, boolean withTableHeader) throws IOException {
        this.properties = properties;
        this.pageProperties = pageProperties;
        this.withTableHeader = withTableHeader;
        document = new PDDocument();
        page = new PDPage();
        document.addPage(page);
        content = new PDPageContentStream(document, page);
    }

    public void writeData(List<PDFRow> rows) throws IOException {
        if (properties.getTitle() != null && !properties.getTitle().isBlank()) {
            addTitle();
        }
        drawTable(rows);
    }

    private void drawTable(List<PDFRow> rows) throws IOException {
        int nbrows = rows.size();
        if (nbrows == 0) {
            return;
        }
        final int nbDatas = nbrows;
        final int cols = rows.get(0).getCellCount();
        final int columnCount = properties.getColumns().size();
        if (columnCount != cols) {
            // Not the same numbers of columns
            final float totalAvailableColumnWidth = MAX_WIDTH - properties.getTotalColumnWidth();
            float colWidth = totalAvailableColumnWidth / (cols - columnCount);
            for (int i = 0; i < (cols - columnCount); i++) {
                properties.addColumn((int) colWidth, "");
            }
        }
        float tableWidth = page.getMediaBox().getWidth() - pageProperties.getMarginLeft() - pageProperties.getMarginRight();
        final float unitColWidth = tableWidth / 19f;

        float y = page.getMediaBox().getHeight() - pageProperties.getPositionFirstLine();

        //draw the rows
        float nexty = y;
        int maxDrawRow = nbrows;
        if (withTableHeader) {
            maxDrawRow++;
            nbrows++;
        }
        int i;
        tableWidth = unitColWidth * properties.getTotalColumnWidth();
        for (i = 0; i <= nbrows; i++) {
            content.moveTo(pageProperties.getMarginLeft(), nexty);
            content.lineTo(pageProperties.getMarginLeft() + tableWidth, nexty);
            content.stroke();
            nexty -= ROW_HEIGHT;
            if ((nexty - pageProperties.getMarginBottom()) < 0) {
                maxDrawRow = i;
                break;
            }
        }

        //draw the columns
        float nextx = pageProperties.getMarginLeft();
        float yEnd = y - (maxDrawRow * ROW_HEIGHT);
        for (i = 0; i <= cols; i++) {
            content.moveTo(nextx, y);
            content.lineTo(nextx, yEnd);
            content.stroke();
            if (i == cols) {
                break;
            }
            nextx += (unitColWidth) * properties.getColumnWidth(i);
        }

        //now add the text
        content.setFont(pageProperties.getFont(), properties.getDefaultFontSize());

        float textx = pageProperties.getMarginLeft() + CELL_MARGIN;
        float texty = y - 15;
        i = 0;
        if (withTableHeader) {
            drawRow(pageProperties, properties.getPDFHeader(), textx, texty, unitColWidth);
            texty -= ROW_HEIGHT;
            i++;
        }
        int countDatas = 0;
        for (PDFRow r : rows) {
            if (i >= maxDrawRow) {
                break;
            }
            i++;
            countDatas++;
            drawRow(pageProperties, r, textx, texty, unitColWidth);
            texty -= ROW_HEIGHT;
        }

        if (countDatas < nbDatas) {
            rows = rows.subList(countDatas, nbDatas);
            content.close();
            page = new PDPage();
            document.addPage(page);
            content = new PDPageContentStream(document, page);
            pageProperties.setPositionFirstLine(pageProperties.getMarginTop());
            drawTable(rows);
            content.close();
        }
    }

    private void drawRow(PDFPageProperties pageProperties, PDFRow row, float textx, float texty, float unitColWidth) throws IOException {
        if (row.getFont() != null) {
            content.setFont(row.getFont(), row.getFontSize());
        } else {
            content.setFont(pageProperties.getFont(), pageProperties.getFontSize());
        }
        int j = 0;
        for (String text : row.getCells()) {
            content.beginText();
            content.newLineAtOffset(textx, texty);
            content.showText(text);
            content.endText();
            textx += (unitColWidth) * properties.getColumnWidth(j);
            j++;
        }
    }

    private void addTitle() throws IOException {
        final String title = properties.getTitle();
        final PDType1Font font = properties.isBoldTitle() ? PDType1Font.HELVETICA_BOLD : PDType1Font.HELVETICA;
        final int fontSize = properties.getTitleFontSize();
        final float titleWidth = font.getStringWidth(title) / 1000 * fontSize;
        final float titleHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;

        content.beginText();
        content.setFont(font, fontSize);
        content.newLineAtOffset((page.getMediaBox().getWidth() - titleWidth) / 2, page.getMediaBox().getHeight() - properties.getMarginBeforeTitle() - titleHeight);
        content.showText(title);
        content.endText();
    }

    public void save(File file) throws IOException {
        content.close();
        document.save(file);
        document.close();
    }
}
