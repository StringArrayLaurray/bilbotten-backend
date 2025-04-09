package org.example.bilbottenbackend.controller;

import org.example.bilbottenbackend.model.CarInfo;
import org.example.bilbottenbackend.service.CarApiService;
import org.example.bilbottenbackend.service.GPTVisionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

//Håndterer billede uploads og interagerer med brugerne gennem API endpoints.
//Funktioner til at uploade billeder, hente bilinfo og sende respons til frontend.

@CrossOrigin(origins = "*")
@RestController
public class UploadController {

    GPTVisionService gptVisionService;
    CarApiService carApiService;

    public UploadController(GPTVisionService gptVisionService, CarApiService carApiService) {
        this.gptVisionService = gptVisionService;
        this.carApiService = carApiService;
    }

   @PostMapping("/upload")
    public Mono<ResponseEntity<CarInfo>> upload(@RequestParam MultipartFile image){
        return gptVisionService.extractPlateFromImage(image)
                .flatMap(plate -> carApiService.getCarInfo(plate))
                .map(carInfo -> ResponseEntity.ok(carInfo));
    }

    @GetMapping("/vehicles/{reg-no-or-vin}")
    public Mono<CarInfo> getCarInfo(@PathVariable("reg-no-or-vin") String registrationNumber){
        return carApiService.getCarInfo(registrationNumber);
    }

}
