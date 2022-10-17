package ru.denis.sensorapi.SensorRestApi.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "sensor")
@Getter
@Setter
@NoArgsConstructor
public class Sensor {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    @Column(name = "name")
    @Size(min = 2, max = 30, message = "Sensor name must be between 2 and 30 characters")
    private String name;

    @OneToMany(mappedBy = "sensor")
    private List<Measurement> measurements;

    @ManyToOne
    @JoinColumn(name = "usr_owner_id", referencedColumnName = "id")
    private User owner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sensor sensor = (Sensor) o;
        return name.equals(sensor.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
