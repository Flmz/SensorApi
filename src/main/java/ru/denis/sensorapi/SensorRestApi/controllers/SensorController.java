package ru.denis.sensorapi.SensorRestApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.denis.sensorapi.SensorRestApi.dto.SensorDTO;
import ru.denis.sensorapi.SensorRestApi.exceptions.sensor.ErrorResponse;
import ru.denis.sensorapi.SensorRestApi.exceptions.sensor.ExistSensorException;
import ru.denis.sensorapi.SensorRestApi.services.SensorService;

import javax.validation.Valid;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PostMapping
    public SensorDTO addSensor(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        return sensorService.save(sensorDTO, bindingResult);
    }


    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(ExistSensorException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
