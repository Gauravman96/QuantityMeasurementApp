package com.QuantityMeasure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.QuantityMeasure.App.Feet;
import com.QuantityMeasure.App.Inches;
import com.QuantityMeasure.App.Length;
import com.QuantityMeasure.App.LengthUnit;

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


    @Test
    public void testInchesEquality_DifferentValue() {

        Inches i1 = new Inches(1.0);

        Inches i2 = new Inches(2.0);

        assertFalse(i1.equals(i2));
    }



   
    // UC3 TEST CASES
    


    @Test
    public void testEquality_FeetToFeet_SameValue() {

        Length l1 = new Length(1.0, LengthUnit.FEET);

        Length l2 = new Length(1.0, LengthUnit.FEET);

        assertTrue(l1.equals(l2));
    }



    @Test
    public void testEquality_InchToInch_SameValue() {

        Length l1 = new Length(1.0, LengthUnit.INCHES);

        Length l2 = new Length(1.0, LengthUnit.INCHES);

        assertTrue(l1.equals(l2));
    }



    @Test
    public void testEquality_FeetToInch_Equivalent() {

        Length l1 = new Length(1.0, LengthUnit.FEET);

        Length l2 = new Length(12.0, LengthUnit.INCHES);

        assertTrue(l1.equals(l2));
    }



    @Test
    public void testEquality_InchToFeet_Equivalent() {

        Length l1 = new Length(12.0, LengthUnit.INCHES);

        Length l2 = new Length(1.0, LengthUnit.FEET);

        assertTrue(l1.equals(l2));
    }



    @Test
    public void testEquality_DifferentValue() {

        Length l1 = new Length(2.0, LengthUnit.FEET);

        Length l2 = new Length(12.0, LengthUnit.INCHES);

        assertFalse(l1.equals(l2));
    }



    @Test
    public void testEquality_SameReference() {

        Length l1 = new Length(1.0, LengthUnit.FEET);

        assertTrue(l1.equals(l1));
    }



    @Test
    public void testEquality_NullComparison() {

        Length l1 = new Length(1.0, LengthUnit.FEET);

        assertFalse(l1.equals(null));
    }


}
