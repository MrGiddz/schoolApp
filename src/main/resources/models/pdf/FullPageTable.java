package main.resources.models.pdf;

/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2020 iText Group NV
    Authors: iText Software.

    For more information, please contact iText Software at this address:
    sales@itextpdf.com
 */

/****
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/19873263/how-to-increase-the-width-of-pdfptable-in-itext-pdf
 */

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import main.resources.models.TableData;
import main.resources.utilities.LoadData;
import main.resources.utilities.notification.Alerts;
import org.apache.xmlgraphics.image.codec.tiff.TIFFEncodeParam;
import org.ghost4j.Ghostscript;
import org.ghost4j.document.DocumentException;
import org.ghost4j.document.PDFDocument;
import org.ghost4j.renderer.RendererException;
import org.ghost4j.renderer.SimpleRenderer;

import javax.print.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FullPageTable {
    private static final String DEST = "./target/sandbox/tables/full_page_table.pdf";

    public static boolean manipulatePdf() throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DEST));
        Document doc = new Document(pdfDoc,  PageSize.A4.rotate());
        doc.setMargins(0, 0, 0, 0);

        Table table = new Table(new float[11]).useAllAvailableWidth();
        table.setMarginTop(0);
        table.setMarginBottom(0);
        table.setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA)).setFontSize(10F);

        // first row
        Cell cell = new Cell(1, 11).add(new Paragraph("Students").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(12F));
        cell.setTextAlignment(TextAlignment.CENTER);
        cell.setPadding(5);
        cell.setBackgroundColor(new DeviceRgb(140, 221, 8));
        table.addCell(cell);

        table.addCell("Full Name");
        table.addCell("Date of Birth");
        table.addCell("Gender");
        table.addCell("Disabled");
        table.addCell("Class");
        table.addCell("Nationality");
        table.addCell("Parent or Guardian");
        table.addCell("Phone Number");
        table.addCell("Email");
        table.addCell("Time Registered");
        table.addCell("Date Registered");


        for (Map.Entry<Integer, TableData> m : LoadData.getStudentFullInfo().entrySet()){
            table.addCell(m.getValue().fullnameProperty().get());
            table.addCell(m.getValue().getDate_of_birth());
            table.addCell(m.getValue().genderProperty().get());
            table.addCell(m.getValue().disabledProperty().get());
            table.addCell(m.getValue().classNameProperty().get());
            table.addCell(m.getValue().nationalityProperty().get());
            table.addCell(m.getValue().parent_nameProperty().get());
            table.addCell(m.getValue().phone_numberProperty().get());
            table.addCell(m.getValue().getStudentEmail().get());
            table.addCell(m.getValue().date_addedProperty().get());
            table.addCell(m.getValue().time_addedProperty().get());
        }

        doc.add(table);

        doc.close();
        return true;
    }

    public static void print() throws IOException {

        FileOutputStream fos = new FileOutputStream("tempFile.pdf");
        byte [] myByteArray = new byte[1024];
        fos.write(myByteArray);
        fos.close();
        fos.flush();


        List<Image> images = null;

        Ghostscript.getInstance(); // create gs instance

        PDFDocument lDocument = new PDFDocument();
        lDocument.load(new File("tempFile.pdf"));

        SimpleRenderer renderer = new SimpleRenderer();

        renderer.setResolution(300);

        try
        {
            images = (List) renderer.render(lDocument);
        }
        catch (RendererException | DocumentException e)
        {
            e.printStackTrace();
        }

        int filename = 1;

        TIFFEncodeParam params = new TIFFEncodeParam();

        Iterator<Image> imageIterator = images.iterator();

        while (imageIterator.hasNext()) {
            BufferedImage image = (BufferedImage) imageIterator.next();

            FileOutputStream os = new FileOutputStream(/*outputDir + */ filename + ".tif");

            JAI.create("encode", image , os, "TIFF", params);

            filename ++;
        }

        FileInputStream in = new FileInputStream(DEST);
        Doc doc = new SimpleDoc(in, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
        PrintService service = PrintServiceLookup.lookupDefaultPrintService();

        int count = 0;
        for (DocFlavor docFlavor : service.getSupportedDocFlavors()) {
            if (docFlavor.toString().contains("pdf")) {
                count++;
            }
        }
        if (count == 0) {
            System.err.println("PDF not supported by printer: " + service.getName());
            Alerts.errorAlert("Error", "PDF not supported by printer: " + service.getName());
        } else {
            System.out.println("PDF is supported by printer: " + service.getName());
        }

        try {
            service.createPrintJob().print(doc, null);
        } catch (PrintException e) {
            e.printStackTrace();
        }
        }

}