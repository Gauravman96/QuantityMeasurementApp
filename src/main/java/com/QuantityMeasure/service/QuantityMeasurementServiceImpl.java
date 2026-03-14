package com.QuantityMeasure.service;

import com.QuantityMeasure.dto.QuantityDTO;
import com.QuantityMeasure.entity.QuantityMeasurementEntity;
import com.QuantityMeasure.repository.IQuantityMeasurementRepository;

public  class QuantityMeasurementServiceImpl
        implements IQuantityMeasurementService {

    private IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean compare(QuantityDTO q1, QuantityDTO q2) {

        boolean result = q1.getValue() == q2.getValue();

        repository.save(
                new QuantityMeasurementEntity(
                        "COMPARE",
                        q1.getValue()+" "+q1.getUnit(),
                        q2.getValue()+" "+q2.getUnit(),
                        String.valueOf(result)
                )
        );

        return result;
    }

    @Override
    public QuantityDTO convert(QuantityDTO source, String targetUnit) {

        QuantityDTO result =
                new QuantityDTO(source.getValue(), targetUnit);

        repository.save(
                new QuantityMeasurementEntity(
                        "CONVERT",
                        source.getValue()+" "+source.getUnit(),
                        null,
                        result.getValue()+" "+targetUnit
                )
        );

        return result;
    }

    @Override
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2) {

        double sum = q1.getValue() + q2.getValue();

        QuantityDTO result =
                new QuantityDTO(sum, q1.getUnit());

        repository.save(
                new QuantityMeasurementEntity(
                        "ADD",
                        q1.getValue()+" "+q1.getUnit(),
                        q2.getValue()+" "+q2.getUnit(),
                        sum+" "+q1.getUnit()
                )
        );

        return result;
    }

    @Override
    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2) {

        double resultVal = q1.getValue() - q2.getValue();

        return new QuantityDTO(resultVal, q1.getUnit());
    }

    @Override
    public double divide(QuantityDTO q1, QuantityDTO q2) {

        if(q2.getValue()==0)
            throw new RuntimeException("Division by zero");

        return q1.getValue()/q2.getValue();
    }
}