package ru.denis.sensorapi.SensorRestApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import ru.denis.sensorapi.SensorRestApi.dto.UserAuthDTO;
import ru.denis.sensorapi.SensorRestApi.dto.UserDTO;
import ru.denis.sensorapi.SensorRestApi.exceptions.authentication.InvalidCredentials;
import ru.denis.sensorapi.SensorRestApi.exceptions.sensor.ErrorResponse;
import ru.denis.sensorapi.SensorRestApi.models.User;
import ru.denis.sensorapi.SensorRestApi.security.jwt.JWTUtil;
import ru.denis.sensorapi.SensorRestApi.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final DaoAuthenticationProvider authProvider;

    @Autowired
    public AuthController(UserService userService,
                          JWTUtil jwtUtil, DaoAuthenticationProvider authProvider) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authProvider = authProvider;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> performRegistration(@RequestBody @Valid UserAuthDTO userAuthDTO) {
        UserDTO user = userService.register(userAuthDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> performLogin(@Valid @RequestBody UserAuthDTO userAuthDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(userAuthDTO.getEmail(),
                        userAuthDTO.getPassword());

        try {
            authProvider.authenticate(authInputToken);
        } catch (AuthenticationException error) {
            throw new InvalidCredentials("Неправильные логин или пароль");
        }
        User user = userService.findByEmail(userAuthDTO.getEmail());
        String token = jwtUtil.generateToken(userAuthDTO.getEmail());

        Map<Object, Object> response = new HashMap<>();
        response.put("login", user.getEmail());
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(InvalidCredentials e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}


