package com.microservice.quantity_service.service;



import java.util.List;


import com.microservice.quantity_service.dto.QuantityInputDTO;
import com.microservice.quantity_service.entity.QuantityMeasurementEntity;


public interface IQuantityMeasurementService {

    QuantityMeasurementEntity compare(QuantityInputDTO input, String username);

    QuantityMeasurementEntity convert(QuantityInputDTO input, String username);

    QuantityMeasurementEntity add(QuantityInputDTO input, String username);

    QuantityMeasurementEntity subtract(QuantityInputDTO input, String username);

    QuantityMeasurementEntity multiply(QuantityInputDTO input, String username);

    QuantityMeasurementEntity divide(QuantityInputDTO input, String username);
}
