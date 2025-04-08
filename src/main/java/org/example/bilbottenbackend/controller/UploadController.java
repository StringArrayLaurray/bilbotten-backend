package org.example.bilbottenbackend.controller;

import org.example.bilbottenbackend.model.CarInfo;
import org.example.bilbottenbackend.service.CarApiService;
//import org.example.bilbottenbackend.service.GPTVisionService;
import org.example.bilbottenbackend.service.GPTVisionService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public UploadController(GPTVisionService gptVisionService, CarApiService carApiService) {
        this.gptVisionService = gptVisionService;
        this.carApiService = carApiService;
    }

   @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam MultipartFile image){
        String plate = gptVisionService.extractPlateFromImage(image).block(); // noget med at .block() skal ændres til flatMap() efter motorAPI kaldet er kodet.
        return ResponseEntity.ok("Extracted: " + plate);

    }

    // til at teste med POSTMAN
  @PostMapping("/uploadPOSTMAN")
    public Mono<String> uploadPOSTMAN(@RequestParam MultipartFile image){
        return gptVisionService.extractPlateFromImage(image);
    }

    @GetMapping("/vehicles/{reg-no-or-vin}")
    public Mono<CarInfo> getCarInfo(@PathVariable("reg-no-or-vin") String registrationNumber){
        return carApiService.getCarInfo(registrationNumber);
    }

}
