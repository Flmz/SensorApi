package ru.denis.sensorapi.SensorRestApi.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "measurement")
@Getter
@Setter
@NoArgsConstructor
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "measurement_id")
    private Long id;

    @Column(name = "value")
    @NotNull
    @Min(value = -100, message = "value of temperature must be greater than -100")
    @Max(value = 100, message = "value of temperature must be less than 100")
    private Double value;

    @Column(name = "raining")
    @NotNull
    private boolean isRaining;

    @Column(name = "make_date")
    private LocalDateTime makeDate;

    @ManyToOne
    @JoinColumn(name = "sensor_name", referencedColumnName = "name")
    private Sensor sensor;
}
