package ru.denis.sensorapi.SensorRestApi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.denis.sensorapi.SensorRestApi.models.enums.Role;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;
    @NotNull

    private String email;

    @NotNull
    @JsonIgnore
    private String password;

    @NotNull
    private Role role;
}
