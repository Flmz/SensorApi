package ru.denis.sensorapi.SensorRestApi.exceptions.user;

public class ExistUserException extends RuntimeException {
    public ExistUserException(String email) {
        super(email + "такой email уже существует");
    }
}
