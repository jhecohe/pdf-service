package com.jhecohe.pdf_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FormularioDto {
    
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    @NotNull(message = "La cédula no puede estar vacía")
    @Size(min = 6, max = 10, message = "La cédula debe tener entre 6 y 10 dígitos")
    private Integer cedula;
    @NotBlank(message = "La ciudad no puede estar vacía")
    private String ciudad;
    @NotBlank(message = "La entidad demandada no puede estar vacía")
    private String entidadDemandada;
    private String fechaRadicado;
    @NotBlank(message = "El nombre del médico no puede estar vacío")
    private String diagnostico;
    @NotBlank(message = "El nombre del médico no puede estar vacío")
    private String tratamiento;

}
