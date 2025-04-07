package org.example.bilbottenbackend.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CarInfo {

    private String registrationNumber;
    private String status;
    private String type;
    private String use;
    private String vin;
    private int totalWeight;
    private int seats;
    private int doors;
    private String make;
    private String model;
    private String variant;
    private int modelYear;
    private String color;
    private String chassisType;
    private int engineCylinders;
    private double  engineVolume;
    private double enginePower;
    private String fuelType;
}
