package com.microservice.quantity_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.microservice.quantity_service.dto.QuantityInputDTO;
import com.microservice.quantity_service.entity.QuantityMeasurementEntity;
import com.microservice.quantity_service.service.IQuantityMeasurementService;

@RestController
@RequestMapping("/api/v1/quantities")
//@CrossOrigin(origins = "http://localhost:4200")
public class QuantityMeasurementController {

    @Autowired
    private IQuantityMeasurementService service;

    // ---------------- OPERATIONS ----------------

    @PostMapping("/compare")
    public QuantityMeasurementEntity compare(
            @RequestBody QuantityInputDTO input,
            @RequestHeader("X-Username") String username) {

        return service.compare(input, username);
    }

    @PostMapping("/convert")
    public QuantityMeasurementEntity convert(
            @RequestBody QuantityInputDTO input,
            @RequestHeader("X-Username") String username) {

        return service.convert(input, username);
    }

    @PostMapping("/add")
    public QuantityMeasurementEntity add(
            @RequestBody QuantityInputDTO input,
            @RequestHeader("X-Username") String username) {

        return service.add(input, username);
    }

    @PostMapping("/subtract")
    public QuantityMeasurementEntity subtract(
            @RequestBody QuantityInputDTO input,
            @RequestHeader("X-Username") String username) {

        return service.subtract(input, username);
    }

    @PostMapping("/multiply")
    public QuantityMeasurementEntity multiply(
            @RequestBody QuantityInputDTO input,
            @RequestHeader("X-Username") String username) {

        return service.multiply(input, username);
    }

    @PostMapping("/divide")
    public QuantityMeasurementEntity divide(
            @RequestBody QuantityInputDTO input,
            @RequestHeader("X-Username") String username) {

        return service.divide(input, username);
    }
}