package ru.denis.sensorapi.SensorRestApi.utils.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.denis.sensorapi.SensorRestApi.dto.SensorDTO;
import ru.denis.sensorapi.SensorRestApi.models.Sensor;
import ru.denis.sensorapi.SensorRestApi.services.SensorService;

@Component
public class SensorValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensor = (SensorDTO) target;

        if (sensorService.findSensorByName(sensor.getName()).isPresent()) {
            errors.rejectValue("name", "", "This sensor already in db");
        }
    }
}
