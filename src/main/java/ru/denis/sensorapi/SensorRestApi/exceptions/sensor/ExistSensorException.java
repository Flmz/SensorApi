package ru.denis.sensorapi.SensorRestApi.exceptions.sensor;

public class ExistSensorException extends RuntimeException {
    private String message;

    public ExistSensorException(String message) {
        super(message);
    }
}
