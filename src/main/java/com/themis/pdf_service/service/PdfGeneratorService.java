package com.themis.pdf_service.service;

import java.io.IOException;
import java.util.LinkedList;

import org.springframework.stereotype.Service;
import com.themis.pdf_service.dto.ComponentDTO;
import com.themis.pdf_service.dto.FormularioDto;
import com.themis.pdf_service.enums.ComponentsPDFEnum;
import com.themis.pdf_service.tools.constants.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PdfGeneratorService {

    private final IPdfGeneratorService pdfGeneratorService;

    public byte[] generatePdf(FormularioDto dto) throws IOException {

        String texto = Formulario.PRIMER_PARRAFO
                .replaceAll("\\[NOMBRE COMPLETO\\]", dto.getNombre())
                .replaceAll("\\[CÉDULA\\]", dto.getCedula().toString())
                .replaceAll("\\[CIUDAD\\]", dto.getCiudad())
                .replaceAll("\\[NOMBRE DE LA ENTIDAD DEMANDADA\\]", dto.getEntidadDemandada());

        String data = texto;

        String hl = "Señor(a) Juez de la República";

        LinkedList<ComponentDTO> components = new LinkedList<>();

        // The components must be added in the order you want them to print.
        components.add(ComponentDTO.builder()
                .text(Formulario.TITULO)
                .bold(true)
                .componentEnum(ComponentsPDFEnum.TITLE)
                .build());

        components.add(ComponentDTO.builder()
                .text(hl)
                .bold(true)
                .componentEnum(ComponentsPDFEnum.HEADLINE)
                .build());

        components.add(ComponentDTO.builder()
                .text(texto)
                .bold(null)
                .componentEnum(ComponentsPDFEnum.PARAGRAPH)
                .build());

        return pdfGeneratorService.print(components);
    }
}
