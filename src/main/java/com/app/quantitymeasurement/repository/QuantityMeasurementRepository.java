package com.app.quantitymeasurement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

@Repository
public interface QuantityMeasurementRepository  extends JpaRepository<QuantityMeasurementEntity, Long> {

    List<QuantityMeasurementEntity> findByOperation(String operation);

    List<QuantityMeasurementEntity> findByThisMeasurementType(String measurementType);

    long countByOperationAndErrorFalse(String operation);

    List<QuantityMeasurementEntity> findByErrorTrue();
    
 // ✅ NEW — get all history for a specific user
    List<QuantityMeasurementEntity> findByUsernameOrderByCreatedAtDesc(String username);

    // ✅ NEW — get history by user and operation
    List<QuantityMeasurementEntity> findByUsernameAndOperationOrderByCreatedAtDesc(
            String username, String operation);
}

