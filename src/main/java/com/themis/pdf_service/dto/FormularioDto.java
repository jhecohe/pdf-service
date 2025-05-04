package com.themis.pdf_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FormularioDto {
    
    @Valid
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    @NotNull(message = "La cédula no puede estar vacía")
    private Integer cedula;
    @NotBlank(message = "La ciudad no puede estar vacía")
    private String ciudad;
    @NotBlank(message = "La entidad demandada no puede estar vacía")
    private String entidadDemandada;
    private String fechaRadicado;
    @NotBlank(message = "El diagnostico no puede estar vacío")
    private String diagnostico;
    @NotBlank(message = "El nombre del tratamiento no puede estar vacío")
    private String tratamiento;

}
