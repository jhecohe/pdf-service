package com.jhecohe.pdf_service.controller;

import java.io.IOException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jhecohe.pdf_service.dto.FormularioDto;
import com.jhecohe.pdf_service.service.PdfGeneratorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PdfController {

    private final PdfGeneratorService pdfGeneratorService;

    @PostMapping("/generate-pdf")
    public ResponseEntity<ByteArrayResource> generatePdf(@RequestBody FormularioDto formularioDto) {
        byte[] pdfBytes;
        try {
            pdfBytes = pdfGeneratorService.generatePdf(formularioDto);
            ByteArrayResource resource = new ByteArrayResource(pdfBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=my_document.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(pdfBytes.length)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResponseEntity.status(500).body(null);
    }
}
