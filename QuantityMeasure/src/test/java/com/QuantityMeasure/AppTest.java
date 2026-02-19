package com.QuantityMeasure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.QuantityMeasure.App.*;



public class AppTest {



    // UC1

    @Test
    public void testFeetEquality_SameValue() {

        Feet f1 = new Feet(1.0);

        Feet f2 = new Feet(1.0);

        assertTrue(f1.equals(f2));

    }



    @Test
    public void testFeetEquality_DifferentValue() {

        Feet f1 = new Feet(1.0);

        Feet f2 = new Feet(2.0);

        assertFalse(f1.equals(f2));

    }



    // UC2

    @Test
    public void testInchesEquality_SameValue() {

        Inches i1 = new Inches(1.0);

        Inches i2 = new Inches(1.0);

        assertTrue(i1.equals(i2));

    }



    // UC3

    @Test
    public void testFeetToInches_Equivalent() {

        Length f = new Length(1.0, LengthUnit.FEET);

        Length i = new Length(12.0, LengthUnit.INCHES);

        assertTrue(f.equals(i));

    }



    // UC4

    @Test
    public void testYardToFeet() {

        Length yard = new Length(1.0, LengthUnit.YARDS);

        Length feet = new Length(3.0, LengthUnit.FEET);

        assertTrue(yard.equals(feet));

    }



    @Test
    public void testYardToInches() {

        Length yard = new Length(1.0, LengthUnit.YARDS);

        Length inch = new Length(36.0, LengthUnit.INCHES);

        assertTrue(yard.equals(inch));

    }



    @Test
    public void testCentimeterToInches() {

        Length cm = new Length(1.0, LengthUnit.CENTIMETERS);

        Length inch = new Length(0.393701, LengthUnit.INCHES);

        assertTrue(cm.equals(inch));

    }



    @Test
    public void testDifferentValues() {

        Length yard = new Length(1.0, LengthUnit.YARDS);

        Length feet = new Length(2.0, LengthUnit.FEET);

        assertFalse(yard.equals(feet));

    }



    @Test
    public void testSameReference() {

        Length yard = new Length(1.0, LengthUnit.YARDS);

        assertTrue(yard.equals(yard));

    }



    @Test
    public void testNullComparison() {

        Length yard = new Length(1.0, LengthUnit.YARDS);

        assertFalse(yard.equals(null));

    }



}
