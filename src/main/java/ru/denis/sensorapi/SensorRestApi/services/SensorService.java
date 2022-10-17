package ru.denis.sensorapi.SensorRestApi.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ru.denis.sensorapi.SensorRestApi.dto.SensorDTO;
import ru.denis.sensorapi.SensorRestApi.exceptions.sensor.ExistSensorException;
import ru.denis.sensorapi.SensorRestApi.models.Sensor;
import ru.denis.sensorapi.SensorRestApi.repositories.SensorRepository;
import ru.denis.sensorapi.SensorRestApi.utils.validators.SensorValidator;

import java.util.Optional;

import static ru.denis.sensorapi.SensorRestApi.utils.Utils.getPrincipal;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorValidator validator;
    private final ModelMapper mapper;
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(@Lazy SensorValidator validator, ModelMapper modelMapper, SensorRepository sensorRepository) {
        this.validator = validator;
        this.mapper = modelMapper;
        this.sensorRepository = sensorRepository;
    }

    public Optional<Sensor> findSensorByName(String name) {
        return sensorRepository.findSensorByNameIgnoreCase(name);
    }

    @Transactional
    public SensorDTO save(SensorDTO sensorDTO, BindingResult bindingResult) {
        Sensor sensor = toEntity(sensorDTO);
        validator.validate(sensor, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ExistSensorException("Сенсор с таким названием уже существует");
        }

        sensor.setOwner(getPrincipal().getUser());
        return toDTO(sensorRepository.save(sensor));
    }

    private Sensor toEntity(SensorDTO sensorDTO) {
        return mapper.map(sensorDTO, Sensor.class);
    }

    private SensorDTO toDTO(Sensor sensor) {
        return mapper.map(sensor, SensorDTO.class);
    }
}
