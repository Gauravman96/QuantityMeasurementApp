package com.QuantityMeasure;

public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;

    private static final double EPSILON = 0.001;

    public Quantity(double value, U unit) {

        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null.");

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid value");

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    public double toBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    public double convertTo(U targetUnit) {

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        if (unit.getClass() != targetUnit.getClass())
            throw new IllegalArgumentException("Incompatible units");

        double baseValue = unit.convertToBaseUnit(value);

        return targetUnit.convertFromBaseUnit(baseValue);
    }

    public Quantity<U> add(Quantity<U> other) {

        validate(other);

        double base = this.toBaseUnit() + other.toBaseUnit();

        double result = unit.convertFromBaseUnit(base);

        return new Quantity<>(result, unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {

        validate(other);

        double base = this.toBaseUnit() + other.toBaseUnit();

        double result = targetUnit.convertFromBaseUnit(base);

        return new Quantity<>(result, targetUnit);
    }

    public Quantity<U> subtract(Quantity<U> other) {

        validate(other);

        double base = this.toBaseUnit() - other.toBaseUnit();

        double result = unit.convertFromBaseUnit(base);

        return new Quantity<>(result, unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {

        validate(other);

        double base = this.toBaseUnit() - other.toBaseUnit();

        double result = targetUnit.convertFromBaseUnit(base);

        return new Quantity<>(result, targetUnit);
    }

    public double divide(Quantity<U> other) {

        validate(other);

        double divisor = other.toBaseUnit();

        if (divisor == 0)
            throw new ArithmeticException("Division by zero");

        return this.toBaseUnit() / divisor;
    }

    private void validate(Quantity<?> other) {

        if (other == null)
            throw new IllegalArgumentException("Operand cannot be null");

        if (!other.unit.getClass().equals(this.unit.getClass()))
            throw new IllegalArgumentException("Incompatible units");
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (!(obj instanceof Quantity<?> other))
            return false;

        if (!other.unit.getClass().equals(this.unit.getClass()))
            return false;

        return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(toBaseUnit());
    }
}