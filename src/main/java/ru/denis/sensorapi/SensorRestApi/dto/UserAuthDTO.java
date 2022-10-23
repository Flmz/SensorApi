package ru.denis.sensorapi.SensorRestApi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAuthDTO {
    private String email;

    private String password;
}
