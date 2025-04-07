package org.example.bilbottenbackend.service;

import org.example.bilbottenbackend.model.AIRequest;
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

//Ansvarlig for at behandle billedet og uddrage nummerpladen ved hjælp af ....

@Service
public class GPTVisionService {

    @Value("${api.key}")
    private String APIKEY;

    public Mono<String> extractPlateFromImage(MultipartFile image) {
        try {
            byte[] bytes = image.getBytes();
            String base64 = Base64.getEncoder().encodeToString(bytes);
            String dataUrl = "data:image/jpeg;base64," + base64;

            WebClient webClient = WebClient.create("https://api.openai.com");

            AIRequest.ContentPart textPart = new AIRequest.ContentPart(
                    "text",
                    "Extract the license plate from the image. Return only the value, e.g. “ZZ28266” or “ZZ 28266”. If you can't find one, reply: null",
                    null);
            AIRequest.ImageUrl imageUrl = new AIRequest.ImageUrl(dataUrl);
            AIRequest.ContentPart imagePart = new AIRequest.ContentPart("image_url", null, imageUrl);

            AIRequest.Message message = new AIRequest.Message("user", List.of(textPart, imagePart));

            AIRequest request = new AIRequest("gpt-4-turbo-2024-04-09", List.of(message), 0.5, 1);

            return webClient.post()
                    .uri("/v1/chat/completions")
                    .header("Authorization", APIKEY)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> response.bodyToMono(String.class)
                                    .flatMap(body -> Mono.error(new RuntimeException("API error: " + body)))
                    )
                    .bodyToMono(AIResponse.class)
                    .map(response -> response.getChoices().get(0).getMessage().getContent());

        } catch (IOException e) {
            return Mono.error(e);
        }
    }
}
