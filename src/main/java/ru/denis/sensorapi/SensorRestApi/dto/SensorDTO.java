package ru.denis.sensorapi.SensorRestApi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SensorDTO {

    private String name;

    private UserDTO owner;

}
