package ru.denis.sensorapi.SensorRestApi.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.denis.sensorapi.SensorRestApi.dto.MeasurementDTO;
import ru.denis.sensorapi.SensorRestApi.dto.MeasurementResponse;
import ru.denis.sensorapi.SensorRestApi.exceptions.sensor.ErrorResponse;
import ru.denis.sensorapi.SensorRestApi.exceptions.sensor.NotExistSensorException;
import ru.denis.sensorapi.SensorRestApi.models.Measurement;
import ru.denis.sensorapi.SensorRestApi.services.MeasurementService;
import ru.denis.sensorapi.SensorRestApi.utils.validators.MeasurementValidator;

import javax.validation.Valid;
import java.util.stream.Collectors;

import static ru.denis.sensorapi.SensorRestApi.utils.Utils.writeErrorMessage;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final MeasurementValidator measurementValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementService measurementService, MeasurementValidator measurementValidator, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.measurementValidator = measurementValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public MeasurementResponse getMeasurements() {
        return new MeasurementResponse(measurementService.getAll()
                .stream()
                .map(this::convertToMeasurementDTO).collect(Collectors.toList()));
    }

    @GetMapping("/rainyDaysCount")
    public long getRainyDays() {
        return measurementService.getRainyDays();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementDTO measurementDTO
            , BindingResult bindingResult) {

        measurementValidator.validate(measurementDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            writeErrorMessage(bindingResult);
        }
        measurementService.save(convertToMeasurement(measurementDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(NotExistSensorException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
}
