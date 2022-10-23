package ru.denis.sensorapi.SensorRestApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.denis.sensorapi.SensorRestApi.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailIgnoreCase(String email);
}
