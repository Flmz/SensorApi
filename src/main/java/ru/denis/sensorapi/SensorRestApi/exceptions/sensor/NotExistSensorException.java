package ru.denis.sensorapi.SensorRestApi.exceptions.sensor;

public class NotExistSensorException extends RuntimeException {

    public NotExistSensorException(String message) {
        super(message);
    }
}
