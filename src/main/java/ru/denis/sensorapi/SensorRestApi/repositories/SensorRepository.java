package ru.denis.sensorapi.SensorRestApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.denis.sensorapi.SensorRestApi.models.Sensor;

import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, String> {
    Optional<Sensor> findSensorByNameIgnoreCase(String name);
}
