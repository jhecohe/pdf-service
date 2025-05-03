package com.jhecohe.pdf_service.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.springframework.stereotype.Service;

import com.jhecohe.pdf_service.dto.FormularioDto;
import com.jhecohe.pdf_service.tools.PdfboxFunctions;
import com.jhecohe.pdf_service.tools.constants.*;

@Service
public class PdfGeneratorService {

    

    public byte[] generatePdf(FormularioDto dto) throws IOException {
        try {

            // Creating PDF document object
            PDDocument document = new PDDocument();

            // Set document properties
            PdfboxFunctions.setDocumentProperties(document);

            // Add a page to the document with proper size
            PDPage page = new PDPage(PDRectangle.LETTER);
            document.addPage(page);

            // Add some Text
            PdfboxFunctions.addText(document, page, Formulario.TITULO, 0, 750);

            // Add paragraph
            String ej = "Ejemplo";
            StringBuilder sb = new StringBuilder();
            String texto = Formulario.PRIMER_PARRAFO.replaceAll("\\[NOMBRE COMPLETO\\]", dto.getNombre());
            texto = texto.replaceAll("\\[CÉDULA\\]", dto.getCedula().toString());
            texto = texto.replaceAll("\\[CIUDAD\\]", dto.getCiudad());
            texto = texto.replaceAll("\\[NOMBRE DE LA ENTIDAD DEMANDADA\\]", dto.getEntidadDemandada());

            String data = texto;
            
            for (int i = 0; i < 2; i++) {
                PdfboxFunctions.addParagraph(document, page, data, 0, 700);
            }

            // Draw Line
            // drawLines(document, page, 25, 500);

            // Draw Rectangle
            // drawRectangle(document, page, 25, 450);

            // Write the document to a byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);

            // Closing the document
            document.close();

            return outputStream.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
