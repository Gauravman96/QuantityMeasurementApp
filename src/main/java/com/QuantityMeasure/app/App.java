package com.QuantityMeasure.app;


import com.QuantityMeasure.controller.QuantityMeasurementController;
import com.QuantityMeasure.dto.QuantityDTO;
import com.QuantityMeasure.repository.QuantityMeasurementCacheRepository;
import com.QuantityMeasure.service.IQuantityMeasurementService;
import com.QuantityMeasure.service.QuantityMeasurementServiceImpl;

public class App {

    public static void main(String[] args) {

        QuantityMeasurementCacheRepository repo =
                QuantityMeasurementCacheRepository.getInstance();

        IQuantityMeasurementService service =
                new QuantityMeasurementServiceImpl(repo);

        QuantityMeasurementController controller =
                new QuantityMeasurementController(service);

        QuantityDTO q1 = new QuantityDTO(1, "FEET");
        QuantityDTO q2 = new QuantityDTO(12, "INCH");

        controller.performComparison(q1, q2);

        controller.performAddition(q1, q2);

        controller.performConversion(q1,"INCH");
    }
}