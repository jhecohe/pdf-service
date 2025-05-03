package com.jhecohe.pdf_service.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.awt.Color;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class PdfboxFunctions {

    static float MARGIN = 25;
    
    public static void setDocumentProperties(PDDocument document) {

        // Creating the PDDocumentInformation object
        PDDocumentInformation docInformation = document.getDocumentInformation();
        // Setting the author of the document
        docInformation.setAuthor("jhecohe");

        // Setting the title of the document
        docInformation.setTitle("Tutela Salud");

        // Setting the creator of the document
        docInformation.setCreator("mysamplecode.com");

        // Setting the subject of the document
        docInformation.setSubject("Declaracio de Echos");

        // Setting the created date of the document
        Calendar date = new GregorianCalendar();
        date.set(2019, 04, 28);
        docInformation.setCreationDate(date);
        docInformation.setModificationDate(date);

        // Setting keywords for the document
        docInformation.setKeywords("Tutela, Salud");

    }

    public static void insertImage(PDDocument document, PDPage page, String imageName, float x, float y) {

        try {
            // Get Content Stream for Writing Data
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Creating PDImageXObject object
            PDImageXObject pdImage = PDImageXObject.createFromFile(imageName, document);

            // Drawing the image in the PDF document
            contentStream.drawImage(pdImage, x, y - 100, 100, 100);

            // Closing the content stream
            contentStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void addText(PDDocument document, PDPage page, String myText, float x, float y) {

        PDRectangle mediabox = page.getMediaBox();
        float startX = mediabox.getLowerLeftX() + MARGIN + x;

        try {
            // Get Content Stream for Writing Data
            PDPageContentStream contentStream = new PDPageContentStream(document, page,
                    PDPageContentStream.AppendMode.APPEND, true);

            // Begin the Content stream
            contentStream.beginText();

            // Setting the font to the Content stream
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);

            // Setting the position for the line
            contentStream.newLineAtOffset(startX, y);

            // Adding text in the form of string
            contentStream.showText(myText);

            // Ending the content stream
            contentStream.endText();

            // Closing the content stream
            contentStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void addParagraph(PDDocument document, PDPage page, String myText, float x, float y) {

        try {


            // Setting the font
            PDFont pdfFont = new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN);
            float fontSize = 12;
            float leading = 1.5f * fontSize;

            // Get the Width and X/Y coordinates
            PDRectangle mediabox = page.getMediaBox();
            float margin = MARGIN;
            float width = mediabox.getWidth() - (2 * margin);
            float startX = mediabox.getLowerLeftX() + margin;
            float startY = mediabox.getUpperRightY() - margin;
            if (x > 0) {
                width = width - x;
                startX = startX + x;
            }
            if (y > 0) {
                startY = y;
            }

            // Split the paragraph based on width and font size into multiple lines
            ArrayList<String> lines = new ArrayList<>();
            int lastSpace = -1;
            while (myText.length() > 0) {
                int spaceIndex = myText.indexOf(' ', lastSpace + 1);
                if (spaceIndex < 0)
                    spaceIndex = myText.length();
                String subString = myText.substring(0, spaceIndex);
                float size = fontSize * pdfFont.getStringWidth(subString) / 1000;
                if (size > width) {
                    if (lastSpace < 0)
                        lastSpace = spaceIndex;
                    subString = myText.substring(0, lastSpace);
                    lines.add(subString);
                    myText = myText.substring(lastSpace).trim();
                    lastSpace = -1;
                } else if (spaceIndex == myText.length()) {
                    lines.add(myText);
                    myText = "";
                } else {
                    lastSpace = spaceIndex;
                }
            }

            // Get Content Stream for Writing Data
            PDPageContentStream contentStream = new PDPageContentStream(document, page,
                    PDPageContentStream.AppendMode.APPEND, true);

            contentStream.beginText();
            contentStream.setFont(pdfFont, fontSize);
            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.newLineAtOffset(startX, startY);
            for (String line : lines) {
                contentStream.showText(line);
                contentStream.newLineAtOffset(0, -leading);
            }
            contentStream.endText();
            contentStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void drawLines(PDDocument document, PDPage page, float x, float y) {

        try {

            PDRectangle mediabox = page.getMediaBox();
            float margin = MARGIN;
            float width = mediabox.getWidth() - (2 * margin);

            // Get Content Stream for Writing Data
            PDPageContentStream contentStream = new PDPageContentStream(document, page,
                    PDPageContentStream.AppendMode.APPEND, true);

            // Setting the non stroking color
            contentStream.setStrokingColor(Color.GREEN);

            // lets make some lines
            contentStream.moveTo(x, y);
            contentStream.lineTo(x + width, y);
            contentStream.lineTo(x + width, y + 25);
            contentStream.lineTo(x, y + 25);
            contentStream.stroke();

            contentStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void drawRectangle(PDDocument document, PDPage page, float x, float y) {

        try {

            PDRectangle mediabox = page.getMediaBox();
            float margin = MARGIN;
            float width = mediabox.getWidth() - (2 * margin);

            // Get Content Stream for Writing Data
            PDPageContentStream contentStream = new PDPageContentStream(document, page,
                    PDPageContentStream.AppendMode.APPEND, true);

            // Setting the non stroking color
            contentStream.setNonStrokingColor(Color.LIGHT_GRAY);

            // Drawing a rectangle
            contentStream.addRect(x, y, width, 25);

            // Drawing a rectangle
            contentStream.fill();

            contentStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
