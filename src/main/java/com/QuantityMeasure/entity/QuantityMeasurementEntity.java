package com.QuantityMeasure.entity;

import java.io.Serializable;

public class QuantityMeasurementEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String operation;
    private String input1;
    private String input2;
    private String result;
    private boolean error;
    private String errorMessage;

    public QuantityMeasurementEntity(String operation, String input1,
                                     String input2, String result) {
        this.operation = operation;
        this.input1 = input1;
        this.input2 = input2;
        this.result = result;
        this.error = false;
    }

    public QuantityMeasurementEntity(String errorMessage) {
        this.error = true;
        this.errorMessage = errorMessage;
    }

    public boolean hasError() {
        return error;
    }

    public String getResult() {
        return result;
    }
}