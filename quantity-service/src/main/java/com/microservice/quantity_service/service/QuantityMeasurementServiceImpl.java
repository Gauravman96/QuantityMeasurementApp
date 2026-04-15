package com.microservice.quantity_service.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservice.quantity_service.dto.QuantityInputDTO;
import com.microservice.quantity_service.entity.QuantityMeasurementEntity;
import com.microservice.quantity_service.repository.QuantityMeasurementRepository;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    @Autowired
    private QuantityMeasurementRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    // ---------------- HISTORY ----------------
    private void sendToHistory(QuantityMeasurementEntity entity) {

        String url = "http://HISTORY-SERVICE/api/v1/history";

        Map<String, Object> history = new HashMap<>();
        history.put("operation", entity.getOperation());
        history.put("resultValue", entity.getResultValue());
        history.put("resultUnit", entity.getResultUnit());
        history.put("username", entity.getUsername());
        history.put("createdAt", LocalDateTime.now());

        restTemplate.postForObject(url, history, Object.class);
    }

    // ---------------- TYPE CHECK ----------------
    private boolean isSameType(String u1, String u2) {
        return getType(u1).equals(getType(u2));
    }

    private String getType(String unit) {

        switch (unit) {
            case "METER":
            case "CM":
            case "INCH":
            case "FEET":
                return "LENGTH";

            case "KG":
            case "GRAM":
            case "POUND":
            case "OUNCE":
                return "WEIGHT";

            case "LITER":
            case "ML":
            case "GALLON":
            case "CUP":
                return "VOLUME";

            default:
                return "UNKNOWN";
        }
    }

    // ---------------- BASE CONVERSION ----------------
    private double convertToBase(double value, String unit) {

        switch (unit) {
            case "METER": return value;
            case "CM": return value / 100;
            case "INCH": return value * 0.0254;
            case "FEET": return value * 0.3048;

            case "KG": return value;
            case "GRAM": return value / 1000;
            case "POUND": return value * 0.453592;
            case "OUNCE": return value * 0.0283495;

            case "LITER": return value;
            case "ML": return value / 1000;
            case "GALLON": return value * 3.78541;
            case "CUP": return value * 0.24;

            default: return value;
        }
    }

    private double convertFromBase(double value, String unit) {

        switch (unit) {
            case "METER": return value;
            case "CM": return value * 100;
            case "INCH": return value / 0.0254;
            case "FEET": return value / 0.3048;

            case "KG": return value;
            case "GRAM": return value * 1000;
            case "POUND": return value / 0.453592;
            case "OUNCE": return value / 0.0283495;

            case "LITER": return value;
            case "ML": return value * 1000;
            case "GALLON": return value / 3.78541;
            case "CUP": return value / 0.24;

            default: return value;
        }
    }

    // ---------------- COMPARE ----------------
    @Override
    public QuantityMeasurementEntity compare(QuantityInputDTO input, String username) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        String u1 = input.getThisQuantityDTO().getUnit();
        String u2 = input.getThatQuantityDTO().getUnit();

        if (!isSameType(u1, u2)) {
            entity.setError(true);
            entity.setErrorMessage("Different types not allowed");
        } else {
            double v1 = convertToBase(input.getThisQuantityDTO().getValue(), u1);
            double v2 = convertToBase(input.getThatQuantityDTO().getValue(), u2);

            boolean result = Math.abs(v1 - v2) < 0.0001;
            entity.setResultString(String.valueOf(result));
        }

        entity.setOperation("COMPARE");
        entity.setUsername(username);
        entity.setCreatedAt(LocalDateTime.now());

        QuantityMeasurementEntity saved = repository.save(entity);
        sendToHistory(saved);
        return saved;
    }

    // ---------------- CONVERT ----------------
    @Override
    public QuantityMeasurementEntity convert(QuantityInputDTO input, String username) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        String from = input.getThisQuantityDTO().getUnit();
        String to = input.getThatQuantityDTO().getUnit();

        if (!isSameType(from, to)) {
            entity.setError(true);
            entity.setErrorMessage("Different types not allowed");
        } else {
            double base = convertToBase(input.getThisQuantityDTO().getValue(), from);
            double result = convertFromBase(base, to);
            
            result = Math.round(result * 100.0) / 100.0;
            entity.setResultValue(result);
            entity.setResultUnit(to);
        }

        entity.setOperation("CONVERT");
        entity.setUsername(username);
        entity.setCreatedAt(LocalDateTime.now());

        QuantityMeasurementEntity saved = repository.save(entity);
        sendToHistory(saved);
        return saved;
    }

    // ---------------- ADD ----------------
    @Override
    public QuantityMeasurementEntity add(QuantityInputDTO input, String username) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        String u1 = input.getThisQuantityDTO().getUnit();
        String u2 = input.getThatQuantityDTO().getUnit();

        if (!isSameType(u1, u2)) {
            entity.setError(true);
            entity.setErrorMessage("Different types not allowed");
        } else {
            double v1 = convertToBase(input.getThisQuantityDTO().getValue(), u1);
            double v2 = convertToBase(input.getThatQuantityDTO().getValue(), u2);

            double result = v1 + v2;

         // 🔥 convert + round
            double finalResult = convertFromBase(result, u1);
            finalResult = Math.round(finalResult * 100.0) / 100.0;
            entity.setResultValue(finalResult);
        }

        entity.setOperation("ADD");
        entity.setUsername(username);
        entity.setCreatedAt(LocalDateTime.now());

        QuantityMeasurementEntity saved = repository.save(entity);
        sendToHistory(saved);
        return saved;
    }

    // ---------------- SUBTRACT ----------------
    @Override
    public QuantityMeasurementEntity subtract(QuantityInputDTO input, String username) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        String u1 = input.getThisQuantityDTO().getUnit();
        String u2 = input.getThatQuantityDTO().getUnit();

        if (!isSameType(u1, u2)) {
            entity.setError(true);
            entity.setErrorMessage("Different types not allowed");
        } else {
            double v1 = convertToBase(input.getThisQuantityDTO().getValue(), u1);
            double v2 = convertToBase(input.getThatQuantityDTO().getValue(), u2);

            double result = v1 - v2;

         // 🔥 convert + round
            double finalResult = convertFromBase(result, u1);
            finalResult = Math.round(finalResult * 100.0) / 100.0;

            entity.setResultValue(finalResult);
        }

        entity.setOperation("SUBTRACT");
        entity.setUsername(username);
        entity.setCreatedAt(LocalDateTime.now());

        QuantityMeasurementEntity saved = repository.save(entity);
        sendToHistory(saved);
        return saved;
    }

    // ---------------- MULTIPLY ----------------
    @Override
    public QuantityMeasurementEntity multiply(QuantityInputDTO input, String username) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        String u1 = input.getThisQuantityDTO().getUnit();
        String u2 = input.getThatQuantityDTO().getUnit();

        if (!isSameType(u1, u2)) {
            entity.setError(true);
            entity.setErrorMessage("Different types not allowed");
        } else {

            double v1 = input.getThisQuantityDTO().getValue();
            double v2 = convertFromBase(
                    convertToBase(input.getThatQuantityDTO().getValue(), u2),
                    u1
            );

            double result = v1 * v2;
         // 🔥 round
            result = Math.round(result * 100.0) / 100.0;

            entity.setResultValue(result);
            entity.setResultUnit(u1);
        }

        entity.setOperation("MULTIPLY");
        entity.setUsername(username);
        entity.setCreatedAt(LocalDateTime.now());

        QuantityMeasurementEntity saved = repository.save(entity);
        sendToHistory(saved);
        return saved;
    }

    // ---------------- DIVIDE ----------------
    @Override
    public QuantityMeasurementEntity divide(QuantityInputDTO input, String username) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        String u1 = input.getThisQuantityDTO().getUnit();
        String u2 = input.getThatQuantityDTO().getUnit();

        double v1 = input.getThisQuantityDTO().getValue();
        double v2 = input.getThatQuantityDTO().getValue();

        if (v2 == 0) {
            entity.setError(true);
            entity.setErrorMessage("Cannot divide by zero");
        }
        else if (!isSameType(u1, u2)) {
            entity.setError(true);
            entity.setErrorMessage("Different types not allowed");
        }
        else {

            double base1 = convertToBase(v1, u1);
            double base2 = convertToBase(v2, u2);

            double baseResult = base1 / base2;

            double finalResult = convertFromBase(baseResult, u1);

            entity.setResultValue(Math.round(finalResult * 100.0) / 100.0);
            entity.setResultUnit(u1);
        }

        entity.setOperation("DIVIDE");
        entity.setUsername(username);
        entity.setCreatedAt(LocalDateTime.now());

        QuantityMeasurementEntity saved = repository.save(entity);
        sendToHistory(saved);
        return saved;
    }
}