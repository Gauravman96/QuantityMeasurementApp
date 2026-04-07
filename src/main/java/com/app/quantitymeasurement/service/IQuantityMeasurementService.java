package com.app.quantitymeasurement.service;



import java.util.List;


import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

public interface IQuantityMeasurementService {


    QuantityMeasurementEntity convert(QuantityInputDTO input);

    QuantityMeasurementEntity add(QuantityInputDTO input);
    
    QuantityMeasurementEntity subtract(QuantityInputDTO input);
    QuantityMeasurementEntity multiply(QuantityInputDTO input);
    QuantityMeasurementEntity divide(QuantityInputDTO input);

    List<QuantityMeasurementEntity> getHistoryByOperation(String operation);
    
    List<QuantityMeasurementEntity> getHistoryByUser(String username);
    
    List<QuantityMeasurementEntity> getHistoryByUserAndOperation(String username, String operation);

    long getOperationCount(String operation);

	QuantityMeasurementEntity compare(QuantityInputDTO input);

	
}
