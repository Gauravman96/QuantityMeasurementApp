package com.QuantityMeasure;

public class App {

    // UC1 and UC2
    public static class Feet {

        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj)
                return true;

            if (obj == null)
                return false;

            if (getClass() != obj.getClass())
                return false;

            Feet other = (Feet) obj;

            return Double.compare(this.value, other.value) == 0;
        }
    }


    // UC2
    public static class Inches {

        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj)
                return true;

            if (obj == null)
                return false;

            if (getClass() != obj.getClass())
                return false;

            Inches other = (Inches) obj;

            return Double.compare(this.value, other.value) == 0;
        }
    }



   
    // UC3 CODE START
  

    public enum LengthUnit {

        FEET(12.0),
        INCHES(1.0);

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double getConversionFactor() {
            return conversionFactor;
        }
    }



    public static class Length {

        private final double value;

        private final LengthUnit unit;


        public Length(double value, LengthUnit unit) {

            if (unit == null)
                throw new IllegalArgumentException("Unit cannot be null");

            this.value = value;

            this.unit = unit;
        }



        private double convertToBaseUnit() {

            return this.value * this.unit.getConversionFactor();
        }



        public boolean compare(Length other) {

            return Double.compare(
                    this.convertToBaseUnit(),
                    other.convertToBaseUnit()) == 0;
        }



        @Override
        public boolean equals(Object obj) {

            if (this == obj)
                return true;

            if (obj == null)
                return false;

            if (getClass() != obj.getClass())
                return false;

            Length other = (Length) obj;

            return this.compare(other);
        }
    }


    // UC3 Demo

    public static void demonstrateLengthEquality() {

        Length l1 = new Length(1.0, LengthUnit.FEET);

        Length l2 = new Length(12.0, LengthUnit.INCHES);

        System.out.println(l1.equals(l2));

    }


    public static void main(String[] args) {

        demonstrateLengthEquality();

    }

}
