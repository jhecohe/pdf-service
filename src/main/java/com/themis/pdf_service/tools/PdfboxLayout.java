package com.themis.pdf_service.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Component;

import com.themis.pdf_service.dto.ComponentDTO;
import com.themis.pdf_service.service.IPdfGeneratorService;
import lombok.extern.slf4j.Slf4j;
import rst.pdfbox.layout.elements.Document;
import rst.pdfbox.layout.elements.Paragraph;
import rst.pdfbox.layout.elements.render.VerticalLayoutHint;
import rst.pdfbox.layout.text.Alignment;
import rst.pdfbox.layout.text.BaseFont;

@Slf4j
@Component
public class PdfboxLayout implements IPdfGeneratorService {

    private static final float PARAGRAPH_FONT = 12.0f;
    private static final float TITLE_FONT = 16.0f;

    private void addTitle(Document document, String text, Boolean bold) {

        PDType1Font font = PDType1Font.HELVETICA;
        if (bold) {
            font = PDType1Font.HELVETICA_BOLD;
        }

        Paragraph paragraph = new Paragraph();
        try {
            paragraph.addText(text.toUpperCase(), TITLE_FONT, font);
        } catch (IOException e) {
            log.error("Error adding title to document: " + e.getMessage());
            e.printStackTrace();
        }
        document.add(paragraph, new VerticalLayoutHint(Alignment.Center, 0, 0,
                40, 20));
    }

    private void addHeadLine(Document document, String text, Boolean bold) {

        PDType1Font font = PDType1Font.HELVETICA;
        if (bold) {
            font = PDType1Font.HELVETICA_BOLD;
        }

        Paragraph paragraph = new Paragraph();
        try {
            paragraph.addText(text, PARAGRAPH_FONT, font);
        } catch (IOException e) {
            log.error("Error adding head Line to document: " + e.getMessage());
            e.printStackTrace();
        }
        document.add(paragraph, new VerticalLayoutHint(Alignment.Left, 0, 0,
                40, PARAGRAPH_FONT));

    }

    private void addParagraph(Document document, String text) {

        Paragraph paragraph = new Paragraph();
        try {
            paragraph.addMarkup(text, 11, BaseFont.Helvetica);
        } catch (IOException e) {
            log.error("Error adding paragraph to document: " + e.getMessage());
            e.printStackTrace();
        }
        document.add(paragraph, new VerticalLayoutHint(Alignment.Justify, 0, 0,
                PARAGRAPH_FONT, 0));

    }

    @Override
    public byte[] print(LinkedList<ComponentDTO> data) {

        // Creating PDF document object
        float hMargin = 70;
        float vMargin = 70;

        Document document = new Document(hMargin, hMargin, vMargin, vMargin);

        while (!data.isEmpty()) {
            ComponentDTO component = data.pop();
            switch (component.getComponentEnum()) {
                case TITLE:
                    addTitle(document, component.getText(), true);
                    break;
                case HEADLINE:
                    addHeadLine(document, component.getText(), true);
                    break;
                case PARAGRAPH:
                    addParagraph(document, component.getText());
                default:
                    break;
            }
        }

        // Write the document to a byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            document.save(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();

    }

}
