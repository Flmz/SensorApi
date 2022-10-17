package ru.denis.sensorapi.SensorRestApi.exceptions.authentication;

public class InvalidCredentials extends RuntimeException {
    public InvalidCredentials(String message) {
        super(message);
    }
}
