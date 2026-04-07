package com.app.quantitymeasurement.controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import org.springframework.security.core.Authentication;


@RestController
@RequestMapping("/api/v1/quantities")
@CrossOrigin(origins = "http://localhost:4200") // ← ADD THIS
public class QuantityMeasurementController {

    @Autowired
    private IQuantityMeasurementService service;

    @PostMapping("/compare")
    public QuantityMeasurementEntity compare(@RequestBody QuantityInputDTO input) {
        return service.compare(input);
    }

    @PostMapping("/convert")
    public QuantityMeasurementEntity convert(@RequestBody QuantityInputDTO input) {
        return service.convert(input);
    }

    @PostMapping("/add")
    public QuantityMeasurementEntity add(@RequestBody QuantityInputDTO input) {
        return service.add(input);
    }

    @PostMapping("/subtract")
    public QuantityMeasurementEntity subtract(@RequestBody QuantityInputDTO input) {
        return service.subtract(input);
    }

    @PostMapping("/multiply")
    public QuantityMeasurementEntity multiply(@RequestBody QuantityInputDTO input) {
        return service.multiply(input);
    }

    @PostMapping("/divide")
    public QuantityMeasurementEntity divide(@RequestBody QuantityInputDTO input) {
        return service.divide(input);
    }
    
    @GetMapping("/history/operation/{operation}")
    public List<QuantityMeasurementEntity> getHistory(@PathVariable String operation) {
        return service.getHistoryByOperation(operation);
    }

    @GetMapping("/count/{operation}")
    public long getCount(@PathVariable String operation) {
        return service.getOperationCount(operation);
    }
    
 
 //  Get ALL history for logged-in user
 @GetMapping("/history/me")
 public List<QuantityMeasurementEntity> getMyHistory(Authentication authentication) {
     String username = authentication.getName();
     return service.getHistoryByUser(username);
 }

 //  Get history by operation for logged-in user
 @GetMapping("/history/me/{operation}")
 public List<QuantityMeasurementEntity> getMyHistoryByOperation(
         Authentication authentication,
         @PathVariable String operation) {
     String username = authentication.getName();
     return service.getHistoryByUserAndOperation(username, operation);
 }
}