package com.QuantityMeasure;


public class App {

    // Inner class
    public static class Feet {

        private final double value;

        // Constructor
        public Feet(double value) {

            this.value = value;

        }

        // equals method override
        @Override
        public boolean equals(Object obj) {

            // same reference
            if (this == obj)
                return true;

            // null check
            if (obj == null)
                return false;

            // class check
            if (getClass() != obj.getClass())
                return false;

            // cast
            Feet other = (Feet) obj;

            // compare values
            return Double.compare(this.value, other.value) == 0;

        }

    }

}
