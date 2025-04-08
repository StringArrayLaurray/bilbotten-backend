package org.example.bilbottenbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CarInfo {

    @JsonProperty("registration_number")
    private String registrationNumber;

    @JsonProperty("status")
    private String status;

    @JsonProperty("status_date")
    private String statusDate;

    @JsonProperty("type")
    private String type;

    @JsonProperty("use")
    private String use;

    @JsonProperty("vin")
    private String vin;

    @JsonProperty("total_weight")
    private int totalWeight;

    @JsonProperty("seats")
    private int seats;

    @JsonProperty("doors")
    private int doors;

    @JsonProperty("make")
    private String make;

    @JsonProperty("model")
    private String model;

    @JsonProperty("variant")
    private String variant;

    @JsonProperty("model_type")
    private String modelType;

    @JsonProperty("model_year")
    private int modelYear;

    @JsonProperty("color")
    private String color;

    @JsonProperty("chassis_type")
    private String chassisType;

    @JsonProperty("engine_cylinders")
    private Integer engineCylinders;

    @JsonProperty("engine_volume")
    private Integer engineVolume;

    @JsonProperty("engine_power")
    private Integer enginePower;

    @JsonProperty("fuel_type")
    private String fuelType;

}
