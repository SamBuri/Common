/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.saburi.common.dbaccess.CompanyDA;
import static com.saburi.common.dbaccess.OptionsDA.getBooleanOptionValue;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.scene.control.TableView;

/**
 *
 * @author Forever
 */
public class PDFDocuments {

    protected String fileName;
    protected Document document;
    protected PdfWriter writer;
    protected static final Font HEADER = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.UNDERLINE, BaseColor.BLUE);
    protected static final Font SUBHEADER = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.UNDERLINE, BaseColor.BLUE);
    protected static final Font BODYNOMAL = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL, BaseColor.BLUE);
    protected static final Font BODYBOLD = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL, BaseColor.BLUE);
    protected static final Font BODY_RED_NORMAL = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD, BaseColor.RED);
    protected static final Font BODYNOMAL_UNDERLINED = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.UNDERLINE, BaseColor.BLUE);
    protected static final Font BODY_RED_UNDERLINED = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.UNDERLINE, BaseColor.RED);
    protected static final Font BEFORE_FOTTER = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.NORMAL, BaseColor.BLUE);
    protected static Font FOTTER = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.NORMAL, BaseColor.RED);
    protected static final Font TABLEHEADER = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD, BaseColor.RED);
    protected static final Font ALEVEL_TABLEHEADER = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD, BaseColor.RED);
    protected static final Font ALEVEL_TABLBODY = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD, BaseColor.BLUE);
    protected static final Font ALEVEL_TABLBODY_RED = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD, BaseColor.RED);
    protected int afterHeader;
    protected CompanyDA companyDA = CompanyDA.getLatestCompanyDA();

    public PDFDocuments(String fileName, Rectangle pageSize) throws FileNotFoundException, DocumentException {
        this.fileName = fileName;
        document = new Document(pageSize);

        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
//            document.open();
            writer.setPageEvent(new PdfHelper());
        } catch (FileNotFoundException | DocumentException ex) {
            throw ex;
        }
    }

    protected void setPageEvents() {
        writer.setPageEvent(new PdfHelper());
    }

    protected void close() {
        document.close();
    }

    protected void openFile() throws IOException {
        try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + fileName);
        } catch (IOException e) {
            throw e;
        }
    }

    protected void addBlankVerticalkSpace(Paragraph p, int space) {
        for (int x = 0; x < space; x++) {
            p.add(new Paragraph(" ", BODYNOMAL));
        }
    }

    protected void addBlankHorizontalSpace(Paragraph p, int space) {
        for (int x = 0; x < space; x++) {
            p.add(new Chunk(" ", BODYNOMAL));
        }
    }

    public PDFDocuments(String fileName, Document document, PdfWriter writer) {
        this.fileName = fileName;
        this.document = document;
        this.writer = writer;
    }

    public String getFileName() {
        return fileName;
    }

    public Document getDocument() {
        return document;
    }

    public PdfWriter getWriter() {
        return writer;
    }

    public Paragraph header() throws DocumentException, IOException {
        Paragraph p = new Paragraph();
        try {
            //load logo

//            File logoFile = new File(logoName);
            if (companyDA != null) {
                if (companyDA.getLogo() != null) {
                    Image logo = Image.getInstance(companyDA.getLogo());

                    Image watermark = logo;

//                    watermark.setTransparency(new int[]{0 * 00, 0 * 2});
//                    watermark.scaleAbsolute(new Rectangle((watermark.getWidth() / 2), (watermark.getHeight() / 2)));
////                    watermark.setAbsolutePosition((document.getPageSize().getWidth() - (watermark.getWidth()) / 2),
////                            document.getPageSize().getHeight()  - (watermark.getHeight()/2));
//
////                    document.add(watermark);
                    ColumnText.showTextAligned(writer.getDirectContentUnder(),
                            Element.ALIGN_CENTER, new Phrase(companyDA.getCompanyName(), HEADER),
                            writer.getPageSize().getWidth()/2, writer.getPageSize().getHeight()/2, 45);
                    logo.scaleAbsolute(new Rectangle(60, 60));
                    logo.setAbsolutePosition(40, document.getPageSize().getHeight() - 80);

                    p.add(logo);

                    PdfContentByte under = writer.getDirectContentUnder();
                    PdfGState state = new PdfGState();
                    under.saveState();
                    state.setFillOpacity(0.3f);
                    under.setGState(state);
                    under.addImage(watermark);
                    under.restoreState();

                }
            }
        } catch (DocumentException | IOException e) {
            throw e;
        }
        int x = 300;
        int y = 20;
        int blanks = 5;

        if (companyDA != null) {
            String phoneNo = companyDA.getPhone();
            String address = companyDA.getPhysicalAddress();
            String email = companyDA.getEmail();
            String webSite = companyDA.getPhysicalAddress();

            String tagLine = companyDA.getTagLine();
//            String unebCentrNo= owner.getUnebCenetreNo();

            Chunk c = new Chunk(companyDA.getCompanyName(), HEADER);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(c),
                    x, document.getPageSize().getHeight() - y, 0);
            if (!address.equals("")) {
                p.add(Chunk.NEWLINE);
                y += 20;
                ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(address, SUBHEADER),
                        x, document.getPageSize().getHeight() - y, 0);

            }

            if (getBooleanOptionValue(CommonOptionKeys.SHOW_COMPANY_PHONE)) {
                if (!phoneNo.equals("")) {
                    p.add(Chunk.NEWLINE);
                    y += 20;
                    ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(phoneNo, SUBHEADER),
                            x, document.getPageSize().getHeight() - y, 0);
                    blanks--;
                }
            }

            if (getBooleanOptionValue(CommonOptionKeys.SHOW_COMPANY_EMAIL)) {
                if (!email.equals("")) {
                    p.add(Chunk.NEWLINE);
                    y += 20;
                    ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(email, SUBHEADER),
                            300, document.getPageSize().getHeight() - y, 0);
                    blanks--;
                }
            }
            if (getBooleanOptionValue(CommonOptionKeys.SHOW_COMPANY_WEBSITE)) {
                if (!webSite.equals("")) {
                    p.add(Chunk.NEWLINE);
                    y += 20;
                    ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(webSite, SUBHEADER),
                            x, document.getPageSize().getHeight() - y, 0);
                    blanks--;
                }
            }

            if (getBooleanOptionValue(CommonOptionKeys.SHOW_TAG_LINE)) {
                if (!tagLine.equals("")) {
                    p.add(Chunk.NEWLINE);
                    y += 20;
                    ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(tagLine, SUBHEADER),
                            x, document.getPageSize().getHeight() - y, 0);
                    blanks--;
                }
            }
//            
//            if (getBooleanOption(OptionNames.SHOW_UNEB_CENTRE_NO.name())) {
//                if (!unebCentrNo.equals("")) {
//                    p.add(Chunk.NEWLINE);
//                    x += 20;
//                    ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("CENTRE NO.: "+unebCentrNo, SUBHEADER),
//                            300, document.getPageSize().getHeight() - x, 0);
//                    blanks--;
//                }
//            }
        }
        p.setAlignment(Element.ALIGN_CENTER);
        addBlankVerticalkSpace(p, blanks);
        afterHeader = y + 20;
        return p;
    }

    public class PdfHelper extends PdfPageEventHelper {

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            try {
                super.onStartPage(writer, document);
                document.add(header());
            } catch (DocumentException | IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            super.onEndPage(writer, document);
            writer.getBoxSize("art");

            Chunk c = new Chunk("_____________________________________________________________________________________________________________", HEADER);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(c), 100, 45, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(new Phrase("Printed via  Schoolsoft on " + new Date(), FOTTER)), 290, 10, 0);

        }
    }

    public void makeTable(TableView tableToPrint, String title) throws IOException, DocumentException {

        int colmns = tableToPrint.getColumns().size();
        int tfont = 7;
        if (colmns <= 10) {
            for (int i = colmns; i <= 10; i++) {
                tfont++;
            }
        }
        List headers = new ArrayList();
        for (int i = 0; i < colmns; i++) {
            headers.add(tableToPrint.getColumns().get(i));
        }

        try {

            tableToPrint.cursorProperty().set(javafx.scene.Cursor.WAIT);
//            String fileName = "C:\\Reports\\report".concat(".pdf");
//            String fileName = System.getProperty("user.home") + "\\" + tableToPrint.getName() + ".pdf";
//            File f = new File(fileName);
//            f.getParentFile().mkdirs();
//            PdfWriter writer = PdfWriter.getInstance(documents, new FileOutputStream(fileName));
            Paragraph ttitle = new Paragraph(title, SUBHEADER);
            ttitle.setAlignment(Element.ALIGN_CENTER);

            this.document.open();
            this.document.add(ttitle);
            addBlankVerticalkSpace(ttitle, 3);
            Paragraph p = new Paragraph("");
            PdfPTable table = new PdfPTable(tableToPrint.getColumns().size());
            table.setWidthPercentage(100);
            table.setSpacingBefore(30f);
            table.setSpacingAfter(0f);

            for (Object o : headers) {

                PdfPCell sb = new PdfPCell(new Paragraph(o.toString(), new Font(Font.FontFamily.TIMES_ROMAN, tfont, Font.BOLD, BaseColor.RED)));
//                sb.setColspan(2);
                table.addCell(sb);
            }

            for (Object o : tableToList(tableToPrint)) {

                PdfPCell sb = new PdfPCell(new Paragraph(o.toString(), new Font(Font.FontFamily.TIMES_ROMAN, tfont, Font.BOLD, BaseColor.BLUE)));
//                sb.setColspan(2);
                table.addCell(sb);
            }

            this.document.add(table);

            this.openFile();

        } catch (DocumentException ex) {
            throw ex;

        } finally {
            this.document.close();
            tableToPrint.cursorProperty().set(javafx.scene.Cursor.DEFAULT);
        }

    }

    private ArrayList tableToList(TableView table) {
        int rows = table.getItems().size();

        int column = table.getColumns().size();
        ArrayList list = new ArrayList();
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < column; y++) {
                list.add(1);
            }
        }

        return list;
    }

}
