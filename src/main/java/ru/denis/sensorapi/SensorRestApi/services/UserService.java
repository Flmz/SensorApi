package ru.denis.sensorapi.SensorRestApi.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.denis.sensorapi.SensorRestApi.dto.UserAuthDTO;
import ru.denis.sensorapi.SensorRestApi.dto.UserDTO;
import ru.denis.sensorapi.SensorRestApi.exceptions.user.ExistUserException;
import ru.denis.sensorapi.SensorRestApi.exceptions.user.NotExistUserException;
import ru.denis.sensorapi.SensorRestApi.models.User;
import ru.denis.sensorapi.SensorRestApi.models.enums.Role;
import ru.denis.sensorapi.SensorRestApi.repositories.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final ModelMapper mapper;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO register(UserAuthDTO userAuthDTO) {
        final String emailToRegister = userAuthDTO.getEmail();

        if (userRepo.findByEmailIgnoreCase(emailToRegister).isPresent()) {
            throw new ExistUserException(emailToRegister);
        }

        User user = toEntity(userAuthDTO);
        enrichUser(user, userAuthDTO);
        return toDto(userRepo.save(user));
    }

    private void enrichUser(User user, UserAuthDTO userAuthDTO) {
        user.setPassword(passwordEncoder.encode(userAuthDTO.getPassword()));
        user.setRole(Role.USER);
    }

    public User findByEmail(String email) {
        Optional<User> userOptional = userRepo.findByEmailIgnoreCase(email);

        if (userOptional.isEmpty()) {
            throw new NotExistUserException("Такого пользователя нет");
        }
        return userOptional.get();
    }

    private User toEntity(UserAuthDTO userAuthDTO) {
        return mapper.map(userAuthDTO, User.class);
    }

    private UserDTO toDto(User user) {
        return mapper.map(user, UserDTO.class);
    }
}
