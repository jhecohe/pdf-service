package com.themis.pdf_service.service;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Service;

@Service
public class PromptService {

    private final WebClient webClient;

    public PromptService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> getPrompt() {

        return webClient
                .get()
                .uri("/nodo")
                .retrieve()
                .bodyToMono(String.class);        
    }
}
