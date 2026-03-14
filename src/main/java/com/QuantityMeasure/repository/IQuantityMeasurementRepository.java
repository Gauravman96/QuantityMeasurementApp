package com.QuantityMeasure.repository;

import java.util.List;

import com.QuantityMeasure.entity.QuantityMeasurementEntity;

public interface IQuantityMeasurementRepository {

    void save(QuantityMeasurementEntity entity);

    List<QuantityMeasurementEntity> findAll();
}