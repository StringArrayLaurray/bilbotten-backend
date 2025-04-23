package org.example.bilbottenbackend.service;

import org.example.bilbottenbackend.model.AIResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class GPTVisionService {

    @Value("${api.key}")
    private String APIKEY;

    public Mono<String> extractPlateFromImage(MultipartFile image) {
        try {
            byte[] bytes = image.getBytes();
            String base64 = Base64.getEncoder().encodeToString(bytes);
            String dataUrl = "data:image/jpeg;base64," + base64;

            WebClient webClient = WebClient.builder()
                    .baseUrl("https://api.openai.com")
                    .defaultHeader("Authorization", "Bearer " + APIKEY)
                    .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .build();

            Map<String, Object> body = Map.of(
                    "model", "gpt-4-turbo-2024-04-09",
                    "messages", List.of(
                            Map.of(
                                    "role", "user",
                                    "content", List.of(
                                            Map.of("type", "text", "text", "Extract the license plate from the image. Return only the value, e.g. 'ZZ28266'. If you can't find one, reply: null."),
                                            Map.of("type", "image_url", "image_url", Map.of("url", dataUrl))
                                    )
                            )
                    ),
                    "temperature", 0.5,
                    "top_p", 1
            );

            return webClient.post()
                    .uri("/v1/chat/completions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(body)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> response.bodyToMono(String.class)
                                    .flatMap(bodyText -> Mono.error(new RuntimeException("API error: " + bodyText)))
                    )
                    .bodyToMono(AIResponse.class)
                    .map(response -> {
                        String raw = response.getChoices().get(0).getMessage().getContent();
                        return raw.toUpperCase().replaceAll("[^A-Z0-9]", "");
                    })
                    .flatMap(cleaned -> {
                        if ("NULL".equalsIgnoreCase(cleaned)) {
                            return Mono.error(new RuntimeException("No plate found"));
                        }
                        return Mono.just(cleaned);
                    });

        } catch (IOException e) {
            return Mono.error(e);
        }
    }
}
