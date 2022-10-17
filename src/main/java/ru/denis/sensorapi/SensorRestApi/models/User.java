package ru.denis.sensorapi.SensorRestApi.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.denis.sensorapi.SensorRestApi.models.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;


@Entity
@Table(name = "usr")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "owner")
    private List<Sensor> sensors;
}
