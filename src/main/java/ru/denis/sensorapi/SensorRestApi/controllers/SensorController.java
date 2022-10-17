package ru.denis.sensorapi.SensorRestApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.denis.sensorapi.SensorRestApi.exceptions.sensor.ErrorResponse;
import ru.denis.sensorapi.SensorRestApi.exceptions.sensor.ExistSensorException;
import ru.denis.sensorapi.SensorRestApi.services.SensorService;
import ru.denis.sensorapi.SensorRestApi.utils.validators.SensorValidator;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorValidator sensorValidator;
    private final SensorService sensorService;

    @Autowired
    public SensorController(SensorValidator sensorValidator, SensorService sensorService) {

        this.sensorValidator = sensorValidator;
        this.sensorService = sensorService;
    }


    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(ExistSensorException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
