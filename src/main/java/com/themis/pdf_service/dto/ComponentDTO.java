package com.themis.pdf_service.dto;

import com.themis.pdf_service.enums.ComponentsPDFEnum;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ComponentDTO {
    String text;
    Boolean bold;
    ComponentsPDFEnum componentEnum;
}
