/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.saburi.common.utils.CommonEnums.NumericDataTypes;
import static com.saburi.common.utils.PDFDocuments.SUBHEADER;
import static com.saburi.common.utils.Utilities.isNullOrEmpty;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author Hp
 */
public class JavaFXPDF extends PDFDocuments {

    private Map<String, Object> headMap;
    private int headerSeperator, startXpos, startYpos, hSpace, vSpace, lineBreak, colDist;
    private TableView<?> tableToPrint;
    private List<PrintableColumn> printableColumns;
    private String title;
    private String[] signitories;
    private String tableHeader;
    private String tableFooter;

    public JavaFXPDF(String fileName, Rectangle pageSize) throws FileNotFoundException, DocumentException {
        super(fileName, pageSize);
    }

    public JavaFXPDF(String fileName, Rectangle pageSize, Map<String, Object> headMap, int headerSeperator,
            int startXpos, int startYpos, int hSpace, int vSpace, int lineBreak, int colDist,
            TableView<?> tableToPrint, List<PrintableColumn> printableColumns,
            String title, String[] signitories, String tableFooter) throws FileNotFoundException, DocumentException {
        super(fileName, pageSize);
        this.headMap = headMap;
        this.headerSeperator = headerSeperator;
        this.startXpos = startXpos;
        this.startYpos = startYpos;
        this.hSpace = hSpace;
        this.vSpace = vSpace;
        this.lineBreak = lineBreak;
        this.colDist = colDist;
        this.tableToPrint = tableToPrint;
        this.printableColumns = printableColumns;
        this.title = title;
        this.signitories = signitories;
        this.tableFooter = tableFooter;
    }

    public JavaFXPDF(String fileName, Rectangle pageSize, Map<String, Object> headMap, int headerSeperator, int startXpos, int startYpos, int hSpace, int vSpace, int lineBreak, int colDist, TableView<?> tableToPrint, List<PrintableColumn> printableColumns, String title, String[] signitories, String tableHeader, String tableFooter) throws FileNotFoundException, DocumentException {
        super(fileName, pageSize);
        this.headMap = headMap;
        this.headerSeperator = headerSeperator;
        this.startXpos = startXpos;
        this.startYpos = startYpos;
        this.hSpace = hSpace;
        this.vSpace = vSpace;
        this.lineBreak = lineBreak;
        this.colDist = colDist;
        this.tableToPrint = tableToPrint;
        this.printableColumns = printableColumns;
        this.title = title;
        this.signitories = signitories;
        this.tableHeader = tableHeader;
        this.tableFooter = tableFooter;
    }

    public void modifyTitle() {
        this.title = this.title.concat(" (COPY)");
    }

    private int getFont(int columns) {
        int tfont = 7;
        if (columns <= 10) {
            for (int i = columns; i <= 10; i++) {
                tfont++;
            }
        }
        return tfont;
    }

    private List<String> getColumnHeader(List<TableColumn> tableColumns) {
        List headers = new ArrayList();

        tableColumns.forEach((column) -> {
            headers.add(column.getText());
        });
        return headers;
    }

    private List<String> getPrintableColumnHeader(List<PrintableColumn> printableColumns) {
        List headers = new ArrayList();

        printableColumns.forEach((column) -> {
            String columnTitle = column.getTitle();
            TableColumn tableColumn = column.getTableColumn();
            if (columnTitle.isBlank()) {
                columnTitle = tableColumn.getText();
            }
            headers.add(columnTitle);
        });
        return headers;
    }

    public PdfPTable getPDFTable(TableView<?> tableToPrint) {

        int colmns = tableToPrint.getColumns().size();
        int rows = tableToPrint.getItems().size();
        var tableColumns = tableToPrint.getColumns();

        try {
            tableToPrint.cursorProperty().set(javafx.scene.Cursor.WAIT);
            int tfont = getFont(colmns);
            ArrayList list = new ArrayList();

            List headers = new ArrayList();

            tableColumns.forEach((column) -> {
                headers.add(column.getText());
            });

            for (int x = 0; x < rows; x++) {

                for (TableColumn col : tableColumns) {
                    list.add(getValue(col.getCellObservableValue(x)));
                }
            }

            PdfPTable table = new PdfPTable(colmns);
            table.setWidthPercentage(100);
            table.setSpacingBefore(30f);
            table.setSpacingAfter(0f);

            for (Object o : headers) {

                PdfPCell sb = new PdfPCell(new Paragraph(o.toString(), new Font(Font.FontFamily.TIMES_ROMAN, tfont, Font.BOLD, BaseColor.RED)));
//                sb.setColspan(2);
                table.addCell(sb);

            }

            for (Object o : list) {

                PdfPCell sb = new PdfPCell(new Paragraph(o == null ? "" : o.toString(), new Font(Font.FontFamily.TIMES_ROMAN, tfont, Font.BOLD, BaseColor.BLUE)));
                table.addCell(sb);
            }

            return table;
        } catch (Exception ex) {
            throw ex;

        } finally {

            tableToPrint.cursorProperty().set(javafx.scene.Cursor.DEFAULT);
        }

    }

    public PdfPTable getPDFTable(TableView<?> tableToPrint, List<TableColumn> tableColumns) {

        int colmns = tableColumns.size();
        int rows = tableToPrint.getItems().size();

        try {
            tableToPrint.cursorProperty().set(javafx.scene.Cursor.WAIT);
            int tfont = getFont(colmns);
            ArrayList list = new ArrayList();
            for (int x = 0; x < rows; x++) {

                for (TableColumn col : tableColumns) {
                    list.add(getValue(col.getCellObservableValue(x)));
                }
            }

            PdfPTable table = new PdfPTable(colmns);
            table.setWidthPercentage(100);
            table.setSpacingBefore(30f);
            table.setSpacingAfter(0f);

            getColumnHeader(tableColumns).stream().map((o) -> new PdfPCell(new Paragraph(o, new Font(Font.FontFamily.TIMES_ROMAN, tfont, Font.BOLD, BaseColor.RED)))).forEachOrdered((sb) -> {
                table.addCell(sb);
            });

            for (Object o : list) {

                PdfPCell sb = new PdfPCell(new Paragraph(o == null ? "" : o.toString(), new Font(Font.FontFamily.TIMES_ROMAN, tfont, Font.BOLD, BaseColor.BLUE)));
                table.addCell(sb);
            }

            return table;
        } catch (Exception ex) {
            throw ex;

        } finally {

            tableToPrint.cursorProperty().set(javafx.scene.Cursor.DEFAULT);
        }

    }

    public PdfPTable getPrintablePDFTable(TableView<?> tableToPrint, List<PrintableColumn> printableColumns) {

        int colmns = printableColumns.size();
        int rows = tableToPrint.getItems().size();

        try {
            tableToPrint.cursorProperty().set(javafx.scene.Cursor.WAIT);
            int tfont = getFont(colmns);
//            ArrayList list = new ArrayList();

            PdfPTable table = new PdfPTable(colmns);
            table.setWidthPercentage(100);
            table.setSpacingBefore(30f);
            table.setSpacingAfter(0f);

            getPrintableColumnHeader(printableColumns).stream().map((o) -> new PdfPCell(new Paragraph(o, new Font(Font.FontFamily.TIMES_ROMAN, tfont, Font.BOLD, BaseColor.RED)))).forEachOrdered((sb) -> {
                table.addCell(sb);
            });

            for (int x = 0; x < rows; x++) {

                for (PrintableColumn printableColumn : printableColumns) {
                    TableColumn tableColumn = printableColumn.getTableColumn();
                    NumericDataTypes numericDataType = printableColumn.getNumericDataType();
                    Object value = getValue(tableColumn.getCellObservableValue(x));
                    String valuyu = value == null ? "" : value.toString();
                    PdfPCell sb = new PdfPCell(new Paragraph(valuyu, new Font(Font.FontFamily.TIMES_ROMAN, tfont, Font.NORMAL, BaseColor.BLUE)));
                    if (numericDataType != null) {
                        sb.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        if (printableColumn.isComputeTotal()) {
                            if (numericDataType.equals(NumericDataTypes.DOUBLE) || numericDataType.equals(NumericDataTypes.FLOAT)) {
                                double totalDouble = printableColumn.getDoubleTotal() + Utilities.defortNumber(valuyu);
                                printableColumn.setDoubleTotal(totalDouble);
                            } else {
                                int intTotal = printableColumn.getIntTotal() + Utilities.defortInteger(valuyu);
                                printableColumn.setIntTotal(intTotal);
                            }
                        }
                    }
                    table.addCell(sb);

                }
            }

            int index = 0;
            boolean addTotals = printableColumns.stream()
                    .filter((p) -> p.isComputeTotal())
                    .map(PrintableColumn::isComputeTotal)
                    .findAny().orElse(false);
            for (PrintableColumn printableColumn : printableColumns) {
//                    TableColumn tableColumn = printableColumn.getTableColumn();
                NumericDataTypes numericDataType = printableColumn.getNumericDataType();

                String value = "";

                if (numericDataType != null) {

                    if (printableColumn.isComputeTotal()) {
                        addTotals = true;
                        if (numericDataType.equals(NumericDataTypes.DOUBLE) || numericDataType.equals(NumericDataTypes.FLOAT)) {
                            value = Utilities.formatNumber((printableColumn.getDoubleTotal()));

                        } else {
                            value = Utilities.formatInteger(printableColumn.getIntTotal());
                        }
                    }
                }

                if (index == 0) {
                    value = "TOTAL";

                }
                PdfPCell sb = new PdfPCell(new Paragraph(value, new Font(Font.FontFamily.TIMES_ROMAN, tfont, Font.BOLD, BaseColor.BLUE)));

                if (index != 0) {
                    sb.setHorizontalAlignment(Element.ALIGN_RIGHT);
                }
                index++;
                if (addTotals) {
                    table.addCell(sb);
                }

            }

            return table;
        } catch (Exception ex) {
            throw ex;

        } finally {

            tableToPrint.cursorProperty().set(javafx.scene.Cursor.DEFAULT);
        }

    }

    public void makeTable(TableView<?> tableToPrint, String title) throws IOException, DocumentException {

        try {
            Paragraph ttitle = new Paragraph(title, SUBHEADER);
            ttitle.setAlignment(Element.ALIGN_CENTER);

            this.document.open();
            this.document.add(ttitle);
            addBlankVerticalkSpace(ttitle, 3);
            PdfPTable table = getPDFTable(tableToPrint);
            table.setWidthPercentage(100);
            table.setSpacingBefore(30f);
            table.setSpacingAfter(0f);

            this.document.add(table);

            this.openFile();

        } catch (DocumentException ex) {
            throw ex;

        } finally {
            this.document.close();

        }

    }

    public void makeTable(TableView<?> tableToPrint, List<TableColumn> tableColumns, String title) throws IOException, DocumentException {

        try {
            Paragraph ttitle = new Paragraph(title, SUBHEADER);
            ttitle.setAlignment(Element.ALIGN_CENTER);

            this.document.open();
            this.document.add(ttitle);
            addBlankVerticalkSpace(ttitle, 3);
            PdfPTable table = getPDFTable(tableToPrint, tableColumns);
            table.setWidthPercentage(100);
            table.setSpacingBefore(30f);
            table.setSpacingAfter(0f);

            this.document.add(table);

            this.openFile();

        } catch (DocumentException ex) {
            throw ex;

        } finally {
            this.document.close();

        }

    }

    private Object getValue(ObservableValue value) {
        try {
            if (isNotNull(value)) {
                return value.getValue();
            }
        } catch (Exception e) {
        }

        return " ";
    }

    private boolean isNotNull(ObservableValue value) {
        try {

            value.getValue();
            return true;
        } catch (Exception e) {
            return false;

        }
    }

    public void makePDFDocument(Map<String, Object> headMap, int startXpos, int startYpos, int hSpace,
            int vSpace, int lineBreak, int colDist,
            TableView<?> tableToPrint, List<TableColumn> tableColumns,
            String title, String[] signitories) throws IOException, DocumentException {

        try {
            Paragraph ttitle = new Paragraph(title, SUBHEADER);
            ttitle.setAlignment(Element.ALIGN_CENTER);

            this.document.open();
            this.document.add(ttitle);
            Paragraph p = new Paragraph();
            int count = 0;
            boolean addVSpace = true;
            int initialYStart = startYpos;
            for (Map.Entry<String, Object> entry : headMap.entrySet()) {

                ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(entry.getKey(), BODYBOLD),
                        startXpos, document.getPageSize().getHeight() - startYpos, 0);

                ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(String.valueOf(entry.getValue()), BODYNOMAL),
                        startXpos + hSpace, document.getPageSize().getHeight() - startYpos, 0);
                startYpos += vSpace;
                count++;
                if (addVSpace) {
                    p.add(Chunk.NEWLINE);
                }
                if (count == lineBreak) {
                    startXpos += startXpos + hSpace + colDist;
                    startYpos = initialYStart;
                    count = 0;
                    addVSpace = false;
                }

            }
            addBlankVerticalkSpace(p, 2);

            PdfPTable table = getPDFTable(tableToPrint, tableColumns);
            table.setWidthPercentage(100);
            table.setSpacingBefore(30f);
            table.setSpacingAfter(0f);
            p.add(table);
            this.document.add(p);
            Paragraph signParagraph = new Paragraph("", BODYNOMAL);
            addBlankVerticalkSpace(signParagraph, 1);
            for (String signitory : signitories) {
                signParagraph.add(signitory + " Signature: ...............................................................................Date: ..............................................");
                signParagraph.add(Chunk.NEWLINE);
            }
            this.document.add(signParagraph);
            this.openFile();

        } catch (DocumentException ex) {
            throw ex;

        } finally {
            this.document.close();

        }

    }

    public void makePrintablePDFDocument() throws IOException, DocumentException {

        try {
            Paragraph titleParagraph = new Paragraph(title, SUBHEADER);
            titleParagraph.setAlignment(Element.ALIGN_CENTER);

            this.document.open();
            this.document.add(titleParagraph);
            Paragraph headerParagraph = new Paragraph();
            int count = 0;
            boolean addVSpace = true;
            int initialYStart = startYpos;
            for (Map.Entry<String, Object> entry : headMap.entrySet()) {

                ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(entry.getKey(), BODYBOLD),
                        startXpos, document.getPageSize().getHeight() - startYpos, 0);

                ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(String.valueOf(entry.getValue()), BODYNOMAL),
                        startXpos + hSpace, document.getPageSize().getHeight() - startYpos, 0);
                startYpos += vSpace;
                count++;
                if (addVSpace) {
                    headerParagraph.add(Chunk.NEWLINE);
                }
                if (count == lineBreak) {
                    startXpos += startXpos + hSpace + colDist;
                    startYpos = initialYStart;
                    count = 0;
                    addVSpace = false;
                }

            }
            addBlankVerticalkSpace(headerParagraph, headerSeperator);
//            document.add(headerParagraph);
//            Paragraph tableParagraph =  new Paragraph(tableHeader, BODYNOMAL);
            PdfPTable table = getPrintablePDFTable(tableToPrint, printableColumns);
            table.setWidthPercentage(100);
            table.setSpacingBefore(30f);
            table.setSpacingAfter(0f);
            if (!isNullOrEmpty(tableHeader)) {
                Paragraph paragraph = new Paragraph("", BODYNOMAL_UNDERLINED);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                paragraph.add(tableHeader);
                headerParagraph.add(paragraph);

            }
            headerParagraph.add(table);
            if (!tableFooter.isBlank()) {
                Paragraph paragraph = new Paragraph(tableFooter, BODYBOLD);
                headerParagraph.add(paragraph);
            }
            this.document.add(headerParagraph);
            Paragraph signParagraph = new Paragraph("", BODYNOMAL);
            addBlankVerticalkSpace(signParagraph, 1);
            for (String signitory : signitories) {
                signParagraph.add(signitory + " Signature: ...............................................................................Date: ..............................................");
                signParagraph.add(Chunk.NEWLINE);
            }
            this.document.add(signParagraph);
            this.openFile();

        } catch (DocumentException ex) {
            throw ex;

        } finally {
            this.document.close();

        }

    }

}
