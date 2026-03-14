package com.QuantityMeasure;

public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;

    private static final double EPSILON = 0.001;

    public Quantity(double value, U unit) {

        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    public Quantity<U> convertTo(U targetUnit) {

        if (unit.getClass() != targetUnit.getClass()) {
            throw new IllegalArgumentException("Incompatible units");
        }

        double baseValue = unit.convertToBaseUnit(value);

        double targetValue = targetUnit.convertFromBaseUnit(baseValue);

        return new Quantity<>(targetValue, targetUnit);
    }

    public Quantity<U> add(Quantity<U> other) {

        unit.validateOperationSupport("ADD");

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        double resultBase = base1 + base2;

        double result = unit.convertFromBaseUnit(resultBase);

        return new Quantity<>(result, unit);
    }

    public Quantity<U> subtract(Quantity<U> other) {

        unit.validateOperationSupport("SUBTRACT");

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        double resultBase = base1 - base2;

        double result = unit.convertFromBaseUnit(resultBase);

        return new Quantity<>(result, unit);
    }

    public double divide(Quantity<U> other) {

        unit.validateOperationSupport("DIVIDE");

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        return base1 / base2;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (!(obj instanceof Quantity)) return false;

        Quantity<?> other = (Quantity<?>) obj;

        if (unit.getClass() != other.unit.getClass())
            return false;

        Quantity<U> converted = ((Quantity<U>) other).convertTo(unit);

        return Math.abs(value - converted.value) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(unit.convertToBaseUnit(value));
    }
}