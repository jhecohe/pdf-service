package com.themis.pdf_service.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import org.springframework.stereotype.Service;
import com.themis.pdf_service.dto.ComponentDTO;
import com.themis.pdf_service.dto.FormularioDto;
import com.themis.pdf_service.enums.ComponentsPDFEnum;
import com.themis.pdf_service.tools.constants.Formulario;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PdfGeneratorService {

    private final IPdfGeneratorService pdfGeneratorService;
    private final PromptService promptService;

    public Mono<byte[]> generatePdf(FormularioDto dto) throws IOException {

        Mono<String> prompt = promptService.getPrompt();

        prompt.switchIfEmpty(Mono.error(new RuntimeException("Prompt is empty")))
                .doOnError(e -> {
                    throw new RuntimeException("Error fetching prompt: " + e.getMessage());
                });

        return prompt.map(data -> {

            byte[] pdf = pdfGeneratorService.print(createComponents(data, dto));

            return pdf;
        });
    }

    private LinkedList<ComponentDTO> createComponents(String data, FormularioDto dto) {

        // Create a list of components to be added to the PDF
        // The order of the components is important for the layout
        // The first component will be printed first, and so on.
        LinkedList<ComponentDTO> components = new LinkedList<>();

        // The components must be added in the order you want them to print.
        components.add(ComponentDTO.builder()
                .text(Formulario.TITULO)
                .bold(true)
                .componentEnum(ComponentsPDFEnum.TITLE)
                .build());

        components.add(ComponentDTO.builder()
                .text(Formulario.SALUDO)
                .bold(true)
                .componentEnum(ComponentsPDFEnum.HEADLINE)
                .build());

        String texto = data
                .replaceAll("\\[NOMBRE COMPLETO\\]", dto.getBoldNombre())
                .replaceAll("\\[CÉDULA\\]", dto.getBoldCedula())
                .replaceAll("\\[CIUDAD\\]", dto.getBoldCiudad())
                .replaceAll("\\[DIAGNOSTICO\\]", dto.getBoldDiagnostico())
                .replaceAll("\\[CONDICION\\]", dto.getBoldCondicion())
                .replaceAll("\\[FECHA\\]", LocalDate.now().toString())
                .replaceAll("\\[ENTIDAD DEMANDADA\\]", dto.getBoldEntidadDemandada());

        components.add(ComponentDTO.builder()
                .text(texto)
                .bold(null)
                .componentEnum(ComponentsPDFEnum.PARAGRAPH)
                .build());

        String firma = Formulario.FIRMA
                .replaceAll("\\[NOMBRE COMPLETO\\]", dto.getBoldNombre())
                .replaceAll("\\[CÉDULA\\]", dto.getCedula())
                .replaceAll("\\[CIUDAD\\]", dto.getCiudad())
                .replaceAll("\\[FECHA\\]", LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")))
                .toString();


        components.add(ComponentDTO.builder()
                .text(firma)
                .bold(null)
                .componentEnum(ComponentsPDFEnum.SIGN)
                .build());

        return components;
    }
}
