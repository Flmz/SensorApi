package ru.denis.sensorapi.SensorRestApi.exceptions.user;

public class NotExistUserException extends RuntimeException {
    public NotExistUserException(String message) {
        super(message);
    }
}
