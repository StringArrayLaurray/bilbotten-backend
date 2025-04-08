package org.example.bilbottenbackend.service;

import org.example.bilbottenbackend.model.CarInfo;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class CarApiService {

    private final WebClient webClient;

    public CarApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<CarInfo> getCarInfo(String registrationNumber) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("vehicles/{reg-no-or-vin}")
                        .build(Map.of("reg-no-or-vin", registrationNumber)))
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> {
                    return Mono.error(new RuntimeException("Kunne ikke hente data fra MotorApi.dk: " + response.statusCode()));
                })
                .bodyToMono(CarInfo.class);
    }
}
