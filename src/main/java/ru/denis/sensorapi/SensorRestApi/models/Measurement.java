package ru.denis.sensorapi.SensorRestApi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "measurements")
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "value")
    @NotNull
    @Min(value = -100, message = "value of temperature must be greater than -100")
    @Max(value = 100, message = "value of temperature must be less than 100")
    private Double value;

    @Column(name = "raining")
    @NotNull
    private boolean isRaining;

    @ManyToOne
    @JoinColumn(referencedColumnName = "name", name = "sensor_name")
    @JsonBackReference
    private Sensor sensor;

    @Column(name = "make_date")
    private LocalDateTime makeDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return isRaining;
    }

    public void setRaining(boolean raining) {
        isRaining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public LocalDateTime getMakeDate() {
        return makeDate;
    }

    public void setMakeDate(LocalDateTime makeDate) {
        this.makeDate = makeDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Measurement that = (Measurement) o;
        return isRaining == that.isRaining && value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, isRaining);
    }
}
