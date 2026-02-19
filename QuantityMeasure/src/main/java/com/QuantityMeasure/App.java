package com.QuantityMeasure;

public class App {

    // UC1
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



    // UC3 + UC4

    public enum LengthUnit {

        FEET(12.0),

        INCHES(1.0),

        YARDS(36.0),

        CENTIMETERS(0.393701);



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

                    other.convertToBaseUnit()

            ) == 0;

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



    public static void main(String[] args) {


        Length yard = new Length(1.0, LengthUnit.YARDS);

        Length feet = new Length(3.0, LengthUnit.FEET);

        Length inch = new Length(36.0, LengthUnit.INCHES);

        Length cm = new Length(1.0, LengthUnit.CENTIMETERS);



        System.out.println(yard.equals(feet));

        System.out.println(yard.equals(inch));

        System.out.println(cm.equals(new Length(0.393701, LengthUnit.INCHES)));

    }

}
