package com.themis.pdf_service.service;

import java.util.LinkedList;

import com.themis.pdf_service.dto.ComponentDTO;

public interface IPdfGeneratorService {

    byte[] print(LinkedList<ComponentDTO> data);
}
