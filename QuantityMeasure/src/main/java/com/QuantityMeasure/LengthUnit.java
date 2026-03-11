package com.QuantityMeasure;


public enum LengthUnit implements IMeasurable {
	FEET(12.0), INCHES(1.0), YARDS(36.0), CENTIMETERS(0.393701);

	private final double conversionFactor;

	private LengthUnit(double conversionFactor) {
		this.conversionFactor = conversionFactor;
	}

	public double getConversionFactor() {
		return conversionFactor;
	}
	
	@Override
	public String getUnitName() {
		return this.toString();
	}
	
	/**
	 * Convert the length value to base unit value
	 */
	public double convertToBaseUnit(double value) {
	    return value * this.conversionFactor;
	}
	 /**
	  * Convert base unit value to specified unit
	  */
	public double convertFromBaseUnit(double baseValue) {
		double convertedValue = baseValue / this.getConversionFactor();
		return convertedValue;
	}
	
	// Main method to test LengthUnit enum
	public static void main(String[] args) {
		double yard = 1.0;
		double inches = LengthUnit.YARDS.convertToBaseUnit(yard);
		System.out.println(yard + " yard is equal to " + inches + " inches"); 
		
		double feet = LengthUnit.FEET.convertFromBaseUnit(inches);
		System.out.println(inches + " inches are equal to " + feet + " feet");
	}
}