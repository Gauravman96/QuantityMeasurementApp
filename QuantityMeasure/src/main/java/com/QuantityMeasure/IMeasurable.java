package com.QuantityMeasure;


	@FunctionalInterface
	interface SupportsArithmetic {
		boolean isSupported();
	}

public interface IMeasurable {
	
	// Default lambda – arithmetic supported
    SupportsArithmetic supportsArithmetic = () -> true;
	
	public double getConversionFactor();
	
	public double convertToBaseUnit(double value);
	
	public double convertFromBaseUnit(double baseValue);
	
	public String getUnitName();
	
	// Optional capability check
    default boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }
    
    // Runtime validation hook
    default void validateOperationSupport(String operation) {
        // Default: allow operations
    }
	
	public static void main(String[] args) {
		System.out.println("IMeasurable Interface!");	
	}
	
}