package com.jhecohe.pdf_service.service;

public interface IPdfGeneratorService {
    byte[] generatePdf() throws Exception;

    byte[] addText() throws Exception;

    byte[] addParagraph() throws Exception;
}
