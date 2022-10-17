package ru.denis.sensorapi.SensorRestApi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.denis.sensorapi.SensorRestApi.models.User;
import ru.denis.sensorapi.SensorRestApi.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> logUser = userRepository.findByEmailIgnoreCase(email);

        if (logUser.isEmpty()) {
            throw new UsernameNotFoundException("user not found");
        }
        return new SecurityUser(logUser.get());
    }
}
