package com.QuantityMeasure;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS,
    FAHRENHEIT,
    KELVIN;

    public double getConversionFactor() {
        return 1;
    }

    public double convertToBaseUnit(double value) {

        switch (this) {

            case CELSIUS:
                return value;

            case FAHRENHEIT:
                return (value - 32) * 5 / 9;

            case KELVIN:
                return value - 273.15;

            default:
                throw new IllegalArgumentException();
        }
    }

    public double convertFromBaseUnit(double baseValue) {

        switch (this) {

            case CELSIUS:
                return baseValue;

            case FAHRENHEIT:
                return (baseValue * 9 / 5) + 32;

            case KELVIN:
                return baseValue + 273.15;

            default:
                throw new IllegalArgumentException();
        }
    }

    public String getUnitName() {
        return name();
    }

    public void validateOperationSupport(String operation) {
        throw new UnsupportedOperationException(
                "Arithmetic operations not supported for Temperature");
    }

    public double convert(double value, TemperatureUnit target) {

        double base = convertToBaseUnit(value);
        return target.convertFromBaseUnit(base);
    }
}