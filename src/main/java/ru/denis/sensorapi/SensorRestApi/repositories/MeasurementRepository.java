package ru.denis.sensorapi.SensorRestApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.denis.sensorapi.SensorRestApi.models.Measurement;
import ru.denis.sensorapi.SensorRestApi.models.Sensor;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    @Query("select count(e) from Measurement e where e.isRaining = ?1")
    long findAllRainingDays(boolean isRain);

    Measurement findBySensor(Sensor sensor);
}
