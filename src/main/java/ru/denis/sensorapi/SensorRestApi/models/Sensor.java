package ru.denis.sensorapi.SensorRestApi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "sensor")
public class Sensor {

    @Id
    @Column(name = "name")
    @Size(min = 2, max = 30, message = "Sensor name must be between 2 and 30 characters")
    private String name;

    @OneToMany(mappedBy = "sensor")

    @JsonManagedReference
    List<Measurement> measurements;

    public Sensor() {
    }

    public Sensor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

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
