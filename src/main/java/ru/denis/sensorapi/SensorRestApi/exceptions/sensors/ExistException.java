package ru.denis.sensorapi.SensorRestApi.exceptions.sensors;

public class ExistException extends RuntimeException {
    private String message;

    public ExistException(String message) {
        super(message);
    }
}
