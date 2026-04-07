package com.app.quantitymeasurement.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    @Autowired
    private QuantityMeasurementRepository repository;

    // ── Get current logged-in username ──────────────
    private String getCurrentUsername() {
        return org.springframework.security.core.context.SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    // ---------------- COMPARE ----------------
    @Override
    public QuantityMeasurementEntity compare(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        double thisValue = input.getThisQuantityDTO().getValue();
        double thatValue = input.getThatQuantityDTO().getValue();
        String thisUnit  = input.getThisQuantityDTO().getUnit();
        String thatUnit  = input.getThatQuantityDTO().getUnit();

        entity.setThisValue(thisValue);
        entity.setThisUnit(thisUnit);
        entity.setThisMeasurementType(input.getThisQuantityDTO().getMeasurementType());
        entity.setThatValue(thatValue);
        entity.setThatUnit(thatUnit);
        entity.setThatMeasurementType(input.getThatQuantityDTO().getMeasurementType());

        double thisValueInInches = convertToInches(thisValue, thisUnit);
        double thatValueInInches = convertToInches(thatValue, thatUnit);

        boolean result = Math.abs(thisValueInInches - thatValueInInches) < 0.0001;

        entity.setOperation("COMPARE");
        entity.setResultString(String.valueOf(result));
        entity.setUsername(getCurrentUsername());        // ← ADD
        entity.setCreatedAt(LocalDateTime.now());        // ← ADD

        return repository.save(entity);
    }

    private double convertToInches(double value, String unit) {
        if (unit.equals("FEET")) return value * 12;
        return value;
    }

    // ---------------- CONVERT ----------------
    @Override
    public QuantityMeasurementEntity convert(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        String fromUnit = input.getThisQuantityDTO().getUnit();
        String toUnit   = input.getThatQuantityDTO().getUnit();
        double value    = input.getThisQuantityDTO().getValue();

        entity.setThisValue(value);
        entity.setThisUnit(fromUnit);
        entity.setThisMeasurementType(input.getThisQuantityDTO().getMeasurementType());
        entity.setThatUnit(toUnit);
        entity.setOperation("CONVERT");

        Double result = null;

        // ── LENGTH ──────────────────────────────────
        if      (fromUnit.equals("FEET")  && toUnit.equals("INCH"))   result = value * 12;
        else if (fromUnit.equals("INCH")  && toUnit.equals("FEET"))   result = value / 12;
        else if (fromUnit.equals("FEET")  && toUnit.equals("CM"))     result = value * 30.48;
        else if (fromUnit.equals("CM")    && toUnit.equals("FEET"))   result = value / 30.48;
        else if (fromUnit.equals("FEET")  && toUnit.equals("METER"))  result = value * 0.3048;
        else if (fromUnit.equals("METER") && toUnit.equals("FEET"))   result = value / 0.3048;
        else if (fromUnit.equals("INCH")  && toUnit.equals("CM"))     result = value * 2.54;
        else if (fromUnit.equals("CM")    && toUnit.equals("INCH"))   result = value / 2.54;
        else if (fromUnit.equals("INCH")  && toUnit.equals("METER"))  result = value * 0.0254;
        else if (fromUnit.equals("METER") && toUnit.equals("INCH"))   result = value / 0.0254;
        else if (fromUnit.equals("METER") && toUnit.equals("CM"))     result = value * 100;
        else if (fromUnit.equals("CM")    && toUnit.equals("METER"))  result = value / 100;

        // ── WEIGHT ──────────────────────────────────
        else if (fromUnit.equals("KG")    && toUnit.equals("GRAM"))   result = value * 1000;
        else if (fromUnit.equals("GRAM")  && toUnit.equals("KG"))     result = value / 1000;
        else if (fromUnit.equals("KG")    && toUnit.equals("POUND"))  result = value * 2.20462;
        else if (fromUnit.equals("POUND") && toUnit.equals("KG"))     result = value / 2.20462;
        else if (fromUnit.equals("KG")    && toUnit.equals("OUNCE"))  result = value * 35.274;
        else if (fromUnit.equals("OUNCE") && toUnit.equals("KG"))     result = value / 35.274;
        else if (fromUnit.equals("GRAM")  && toUnit.equals("POUND"))  result = value * 0.00220462;
        else if (fromUnit.equals("POUND") && toUnit.equals("GRAM"))   result = value / 0.00220462;
        else if (fromUnit.equals("GRAM")  && toUnit.equals("OUNCE"))  result = value * 0.035274;
        else if (fromUnit.equals("OUNCE") && toUnit.equals("GRAM"))   result = value / 0.035274;
        else if (fromUnit.equals("POUND") && toUnit.equals("OUNCE"))  result = value * 16;
        else if (fromUnit.equals("OUNCE") && toUnit.equals("POUND"))  result = value / 16;

        // ── VOLUME ──────────────────────────────────
        else if (fromUnit.equals("LITER")  && toUnit.equals("ML"))     result = value * 1000;
        else if (fromUnit.equals("ML")     && toUnit.equals("LITER"))  result = value / 1000;
        else if (fromUnit.equals("LITER")  && toUnit.equals("GALLON")) result = value * 0.264172;
        else if (fromUnit.equals("GALLON") && toUnit.equals("LITER"))  result = value / 0.264172;
        else if (fromUnit.equals("LITER")  && toUnit.equals("CUP"))    result = value * 4.22675;
        else if (fromUnit.equals("CUP")    && toUnit.equals("LITER"))  result = value / 4.22675;
        else if (fromUnit.equals("ML")     && toUnit.equals("GALLON")) result = value * 0.000264172;
        else if (fromUnit.equals("GALLON") && toUnit.equals("ML"))     result = value / 0.000264172;
        else if (fromUnit.equals("ML")     && toUnit.equals("CUP"))    result = value * 0.00422675;
        else if (fromUnit.equals("CUP")    && toUnit.equals("ML"))     result = value / 0.00422675;
        else if (fromUnit.equals("GALLON") && toUnit.equals("CUP"))    result = value * 16;
        else if (fromUnit.equals("CUP")    && toUnit.equals("GALLON")) result = value / 16;

        // ── Same unit ────────────────────────────────
        else if (fromUnit.equals(toUnit)) result = value;

        // ── Not supported ────────────────────────────
        if (result == null) {
            entity.setError(true);
            entity.setErrorMessage(
                "Conversion from " + fromUnit + " to " + toUnit + " is not supported."
            );
        } else {
            result = Math.round(result * 10000.0) / 10000.0;
            entity.setResultValue(result);
            entity.setResultUnit(toUnit);
        }

        entity.setUsername(getCurrentUsername());        // ← ADD
        entity.setCreatedAt(LocalDateTime.now());        // ← ADD

        return repository.save(entity);
    }

    // ---------------- ADD ----------------
    @Override
    public QuantityMeasurementEntity add(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        double thisValue = input.getThisQuantityDTO().getValue();
        double thatValue = input.getThatQuantityDTO().getValue();
        String thisUnit  = input.getThisQuantityDTO().getUnit();
        String thatUnit  = input.getThatQuantityDTO().getUnit();

        entity.setThisValue(thisValue);
        entity.setThisUnit(thisUnit);
        entity.setThisMeasurementType(input.getThisQuantityDTO().getMeasurementType());
        entity.setThatValue(thatValue);
        entity.setThatUnit(thatUnit);
        entity.setThatMeasurementType(input.getThatQuantityDTO().getMeasurementType());

        if (thisUnit.equals("FEET")) thisValue = thisValue * 12;
        if (thatUnit.equals("FEET")) thatValue = thatValue * 12;

        double resultInInches = thisValue + thatValue;
        double resultInFeet   = resultInInches / 12;

        entity.setResultValue(resultInFeet);
        entity.setResultUnit("FEET");
        entity.setOperation("ADD");
        entity.setUsername(getCurrentUsername());        // ← ADD
        entity.setCreatedAt(LocalDateTime.now());        // ← ADD

        return repository.save(entity);
    }

    // ---------------- SUBTRACT ----------------
    @Override
    public QuantityMeasurementEntity subtract(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        double thisValue = input.getThisQuantityDTO().getValue();
        double thatValue = input.getThatQuantityDTO().getValue();
        String thisUnit  = input.getThisQuantityDTO().getUnit();
        String thatUnit  = input.getThatQuantityDTO().getUnit();

        entity.setThisValue(thisValue);
        entity.setThisUnit(thisUnit);
        entity.setThisMeasurementType(input.getThisQuantityDTO().getMeasurementType());
        entity.setThatValue(thatValue);
        entity.setThatUnit(thatUnit);
        entity.setThatMeasurementType(input.getThatQuantityDTO().getMeasurementType());

        if (thisUnit.equals("FEET")) thisValue = thisValue * 12;
        if (thatUnit.equals("FEET")) thatValue = thatValue * 12;

        double resultInInches = thisValue - thatValue;
        double resultInFeet   = resultInInches / 12;

        entity.setResultValue(resultInFeet);
        entity.setResultUnit("FEET");
        entity.setOperation("SUBTRACT");
        entity.setUsername(getCurrentUsername());        // ← ADD
        entity.setCreatedAt(LocalDateTime.now());        // ← ADD

        return repository.save(entity);
    }

    // ---------------- MULTIPLY ----------------
    @Override
    public QuantityMeasurementEntity multiply(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        double thisValue = input.getThisQuantityDTO().getValue();
        double thatValue = input.getThatQuantityDTO().getValue();
        String thisUnit  = input.getThisQuantityDTO().getUnit();
        String thatUnit  = input.getThatQuantityDTO().getUnit();

        entity.setThisValue(thisValue);
        entity.setThisUnit(thisUnit);
        entity.setThisMeasurementType(input.getThisQuantityDTO().getMeasurementType());
        entity.setThatValue(thatValue);
        entity.setThatUnit(thatUnit);
        entity.setThatMeasurementType(input.getThatQuantityDTO().getMeasurementType());

        if (thisUnit.equals("FEET")) thisValue = thisValue * 12;
        if (thatUnit.equals("FEET")) thatValue = thatValue * 12;

        double resultInInches = thisValue * thatValue;
        double resultInFeet   = resultInInches / 12;

        entity.setResultValue(resultInFeet);
        entity.setResultUnit("FEET");
        entity.setOperation("MULTIPLY");
        entity.setUsername(getCurrentUsername());        // ← ADD
        entity.setCreatedAt(LocalDateTime.now());        // ← ADD

        return repository.save(entity);
    }

    // ---------------- DIVIDE ----------------
    @Override
    public QuantityMeasurementEntity divide(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        double thisValue = input.getThisQuantityDTO().getValue();
        double thatValue = input.getThatQuantityDTO().getValue();
        String thisUnit  = input.getThisQuantityDTO().getUnit();
        String thatUnit  = input.getThatQuantityDTO().getUnit();

        entity.setThisValue(thisValue);
        entity.setThisUnit(thisUnit);
        entity.setThisMeasurementType(input.getThisQuantityDTO().getMeasurementType());
        entity.setThatValue(thatValue);
        entity.setThatUnit(thatUnit);
        entity.setThatMeasurementType(input.getThatQuantityDTO().getMeasurementType());

        if (thatValue == 0) {
            entity.setError(true);
            entity.setErrorMessage("Cannot divide by zero.");
            entity.setOperation("DIVIDE");
            entity.setUsername(getCurrentUsername());    // ← ADD
            entity.setCreatedAt(LocalDateTime.now());    // ← ADD
            return repository.save(entity);
        }

        if (thisUnit.equals("FEET")) thisValue = thisValue * 12;
        if (thatUnit.equals("FEET")) thatValue = thatValue * 12;

        double resultInInches = thisValue / thatValue;
        double resultInFeet   = resultInInches / 12;

        entity.setResultValue(resultInFeet);
        entity.setResultUnit("FEET");
        entity.setOperation("DIVIDE");
        entity.setUsername(getCurrentUsername());        // ← ADD
        entity.setCreatedAt(LocalDateTime.now());        // ← ADD

        return repository.save(entity);
    }

    // ---------------- HISTORY BY USER ----------------
    @Override
    public List<QuantityMeasurementEntity> getHistoryByUser(String username) {
        return repository.findByUsernameOrderByCreatedAtDesc(username);
    }

    // ---------------- HISTORY BY USER + OPERATION ----------------
    @Override
    public List<QuantityMeasurementEntity> getHistoryByUserAndOperation(
            String username, String operation) {
        return repository.findByUsernameAndOperationOrderByCreatedAtDesc(username, operation);
    }

    // ---------------- HISTORY BY OPERATION ----------------
    @Override
    public List<QuantityMeasurementEntity> getHistoryByOperation(String operation) {
        return repository.findByOperation(operation);
    }

    // ---------------- COUNT ----------------
    @Override
    public long getOperationCount(String operation) {
        return repository.countByOperationAndErrorFalse(operation);
    }
}