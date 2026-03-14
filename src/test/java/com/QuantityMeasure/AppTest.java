package com.QuantityMeasure;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.QuantityMeasure.controller.QuantityMeasurementController;
import com.QuantityMeasure.dto.QuantityDTO;
import com.QuantityMeasure.repository.QuantityMeasurementCacheRepository;
import com.QuantityMeasure.service.IQuantityMeasurementService;
import com.QuantityMeasure.service.QuantityMeasurementServiceImpl;

public class AppTest {

    private IQuantityMeasurementService service;
    private QuantityMeasurementController controller;

    @BeforeEach
    void setUp() {

        service = new QuantityMeasurementServiceImpl(
                QuantityMeasurementCacheRepository.getInstance());

        controller = new QuantityMeasurementController(service);
    }

    // Entity / DTO Tests

    @Test
    void testQuantityDTO_Creation() {

        QuantityDTO dto = new QuantityDTO(10, "FEET");

        assertEquals(10, dto.getValue());
        assertEquals("FEET", dto.getUnit());
    }

    // Service Layer Tests
    @Test
    void testService_CompareEquality_SameUnit() {

        QuantityDTO q1 = new QuantityDTO(12, "FEET");
        QuantityDTO q2 = new QuantityDTO(12, "FEET");

        assertTrue(service.compare(q1, q2));
    }

    @Test
    void testService_CompareEquality_DifferentUnit() {

        QuantityDTO q1 = new QuantityDTO(12, "FEET");
        QuantityDTO q2 = new QuantityDTO(12, "INCH");

        assertFalse(service.compare(q1, q2));
    }

    @Test
    void testService_Convert() {

        QuantityDTO source = new QuantityDTO(1, "FEET");

        QuantityDTO result = service.convert(source, "INCH");

        assertEquals("INCH", result.getUnit());
    }

    @Test
    void testService_Addition() {

        QuantityDTO q1 = new QuantityDTO(12, "FEET");
        QuantityDTO q2 = new QuantityDTO(12, "FEET");

        QuantityDTO result = service.add(q1, q2);

        assertEquals(24, result.getValue());
        assertEquals("FEET", result.getUnit());
    }

    @Test
    void testService_Subtraction() {

        QuantityDTO q1 = new QuantityDTO(12, "FEET");
        QuantityDTO q2 = new QuantityDTO(12, "FEET");

        QuantityDTO result = service.subtract(q1, q2);

        assertEquals(0, result.getValue());
    }

    @Test
    void testService_Division() {

        QuantityDTO q1 = new QuantityDTO(12, "FEET");
        QuantityDTO q2 = new QuantityDTO(12, "FEET");

        double result = service.divide(q1, q2);

        assertEquals(1, result);
    }

    @Test
    void testService_Division_ByZero() {

        QuantityDTO q1 = new QuantityDTO(12, "FEET");
        QuantityDTO q2 = new QuantityDTO(12, "FEET");

        assertThrows(RuntimeException.class,
                () -> service.divide(q1, q2));
    }

    // Controller Layer Tests

    @Test
    void testController_CompareOperation() {

        QuantityDTO q1 = new QuantityDTO(12, "FEET");
        QuantityDTO q2 = new QuantityDTO(12, "FEET");

        assertDoesNotThrow(() ->
                controller.performComparison(q1, q2));
    }

    @Test
    void testController_AdditionOperation() {

        QuantityDTO q1 = new QuantityDTO(12, "FEET");
        QuantityDTO q2 = new QuantityDTO(12, "FEET");

        assertDoesNotThrow(() ->
                controller.performAddition(q1, q2));
    }

    @Test
    void testController_ConversionOperation() {

        QuantityDTO q1 = new QuantityDTO(1, "FEET");

        assertDoesNotThrow(() ->
                controller.performConversion(q1, "INCH"));
    }

    // Integration Test
    @Test
    void testIntegration_EndToEnd_Addition() {

        QuantityDTO q1 = new QuantityDTO(12, "FEET");
        QuantityDTO q2 = new QuantityDTO(12, "FEET");

        QuantityDTO result = service.add(q1, q2);

        assertEquals(24, result.getValue());
    }
}