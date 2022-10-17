package ru.denis.sensorapi.SensorRestApi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.denis.sensorapi.SensorRestApi.dto.SensorDTO;
import ru.denis.sensorapi.SensorRestApi.exceptions.sensor.ErrorResponse;
import ru.denis.sensorapi.SensorRestApi.exceptions.sensor.ExistSensorException;
import ru.denis.sensorapi.SensorRestApi.services.SensorService;
import ru.denis.sensorapi.SensorRestApi.utils.validators.SensorValidator;

import javax.validation.Valid;

@RestController
public class UserController {
    private final SensorValidator validator;
    private final SensorService sensorService;

    public UserController(SensorValidator validator, SensorService sensorService) {
        this.validator = validator;
        this.sensorService = sensorService;
    }

    @PostMapping("/add_sensor")
    public ResponseEntity<?> addSensor(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        validator.validate(sensorDTO, bindingResult);
        SensorDTO sensorToSave = sensorService.save(sensorDTO, bindingResult);
        return new ResponseEntity<>(sensorToSave, HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(ExistSensorException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}

