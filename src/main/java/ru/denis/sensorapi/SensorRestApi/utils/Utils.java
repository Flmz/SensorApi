package ru.denis.sensorapi.SensorRestApi.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.denis.sensorapi.SensorRestApi.exceptions.sensor.ExistSensorException;
import ru.denis.sensorapi.SensorRestApi.security.SecurityUser;

import java.util.List;

public class Utils {

    public static void writeErrorMessage(BindingResult bindingResult) {
        StringBuilder errorMsg = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            errorMsg.append(error.getField())
                    .append(" - ").append(error.getDefaultMessage())
                    .append(";");
        }
        throw new ExistSensorException(errorMsg.toString());
    }

    public static SecurityUser getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (SecurityUser) authentication.getPrincipal();
    }
}
