package com.QuantityMeasure;


public interface IMeasurable {
	
	public double getConversionFactor();
	
	public double convertToBaseUnit(double value);
	
	public double convertFromBaseUnit(double baseValue);
	
	public String getUnitName();
	
	public static void main(String[] args) {
		System.out.println("IMeasurable Interface!");	
	}
}