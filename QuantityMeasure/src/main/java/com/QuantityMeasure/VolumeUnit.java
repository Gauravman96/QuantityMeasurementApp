package com.QuantityMeasure;


public enum VolumeUnit implements IMeasurable{
	
	// Conversion factor to the base unit(grams)
	LITRE(1.0), MILLILITRE(0.001), GALLON(3.78541);
	
	private final double conversionFactor;

	private VolumeUnit(double conversionFactor) {
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
		return Math.round(convertedValue * 100.0) / 100.0;
	}
	
	public static void main(String[] args) {
		double millilitre = 5000.0;
		double litre = VolumeUnit.MILLILITRE.convertToBaseUnit(millilitre);
		System.out.println("5000.0 Millilitre = " + litre + " Litre");
		
		double gallon = VolumeUnit.GALLON.convertFromBaseUnit(litre);
		System.out.println("5.0 Litre = " + gallon + " Gallon");
	}
}