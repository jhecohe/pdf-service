package com.themis.pdf_service.controller;

import java.io.IOException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.themis.pdf_service.dto.FormularioDto;
import com.themis.pdf_service.service.PdfGeneratorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class PdfController {

    private final PdfGeneratorService pdfGeneratorService;

    @PostMapping("/generate-pdf")
    public Mono<ResponseEntity<ByteArrayResource>> generatePdf(@Valid @RequestBody FormularioDto formularioDto) {
        Mono<byte[]> pdfBytes;
        try {
            pdfBytes = pdfGeneratorService.generatePdf(formularioDto);

            return pdfBytes.map(bytes -> {
                ByteArrayResource resource = new ByteArrayResource(bytes);

                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=documento.pdf");

                return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(bytes.length)
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(resource);
            });
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Mono.just(ResponseEntity.status(500).body(null));
    }
}
