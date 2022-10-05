package ru.denis.sensorapi.SensorRestApi.utils.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.denis.sensorapi.SensorRestApi.dto.MeasurementDTO;
import ru.denis.sensorapi.SensorRestApi.services.MeasurementService;
import ru.denis.sensorapi.SensorRestApi.services.SensorService;

@Component
public class MeasurementValidator implements Validator {
    private final MeasurementService measurementService;
    private final SensorService sensorService;

    public MeasurementValidator(MeasurementService measurementService, SensorService sensorService) {
        this.measurementService = measurementService;
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MeasurementDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurement = (MeasurementDTO) target;

        if (sensorService.findSensorByName(measurement.getSensor().getName()).isEmpty()) {
            errors.rejectValue("sensor", "", "No sensor with this name");
        }

    }
}
