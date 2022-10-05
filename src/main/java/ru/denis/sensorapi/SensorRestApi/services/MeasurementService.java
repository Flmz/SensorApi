package ru.denis.sensorapi.SensorRestApi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.denis.sensorapi.SensorRestApi.exceptions.sensors.NotExistException;
import ru.denis.sensorapi.SensorRestApi.models.Measurement;
import ru.denis.sensorapi.SensorRestApi.models.Sensor;
import ru.denis.sensorapi.SensorRestApi.repositories.MeasurementRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
    }

    @Transactional
    public void save(Measurement measurement) {
        Optional<Sensor> sensor = sensorService.findSensorByName(measurement.getSensor().getName());
        if (sensor.isEmpty()) {
            throw new NotExistException("No sensor with this name");
        } else {
            enrichMeasurement(measurement, sensor);
            measurementRepository.save(measurement);
        }
    }

    public List<Measurement> getAll() {
        return measurementRepository.findAll();
    }

    public long getRainyDays() {
        return measurementRepository.findAllRainingDays(true);
    }

    public void enrichMeasurement(Measurement measurement, Optional<Sensor> sensor) {
        measurement.setSensor(sensor.get());
        measurement.setMakeDate(LocalDateTime.now());
    }
}
