package by.epam.training.homework27.controller;

import by.epam.training.homework27.dto.UserDto;
import by.epam.training.homework27.security.jwt.JwtProvider;
import by.epam.training.homework27.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService,
                          JwtProvider jwtProvider,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<Object, Object>> register(@Valid @RequestBody UserDto userDto) {

        UserDto savedUser = userService.save(userDto);

        String currentUri = fromCurrentRequestUri().toUriString();
        String savedUserLocation = currentUri + '/' + savedUser.getId();
        Map<Object, Object> response = new HashMap<>();
        response.put("message", "Registration is successful");

        return status(CREATED)
                .header(LOCATION, savedUserLocation)
                .body(response);

    }

    @PostMapping("/login")
    public ResponseEntity<Map<Object, Object>> login(@Valid @RequestBody UserDto userDto) {
        Map<Object, Object> response;

        if (isRegistered(userDto.getUsername(), userDto.getPassword())) {

            String token = jwtProvider.generateToken(userDto.getUsername());

            response = new HashMap<>();
            response.put("username", userDto.getUsername());
            response.put("token", token);
        } else {
            throw new BadCredentialsException("Bad credentials or user not registered");
        }
        return ResponseEntity.status(CREATED)
                .body(response);
    }

    private boolean isRegistered(String username, String password) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        if (authenticate.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            return true;
        }
        return false;
    }
}
