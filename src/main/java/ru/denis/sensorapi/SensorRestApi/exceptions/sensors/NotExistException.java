package ru.denis.sensorapi.SensorRestApi.exceptions.sensors;

public class NotExistException extends RuntimeException {

    public NotExistException(String message) {
        super(message);
    }
}
