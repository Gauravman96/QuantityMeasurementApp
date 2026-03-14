package com.QuantityMeasure;

public interface IMeasurable {

    double getConversionFactor();

    double convertToBaseUnit(double value);

    double convertFromBaseUnit(double baseValue);

    String getUnitName();

    default void validateOperationSupport(String operation) {
    }
}