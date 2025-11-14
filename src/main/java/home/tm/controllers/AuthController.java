package home.tm.controllers;

import home.tm.TimeKeeper.api.controllers.UsersApi;
import home.tm.TimeKeeper.api.models.AuthRequest;
import home.tm.TimeKeeper.api.models.AuthResponse;
import home.tm.TimeKeeper.api.models.RegistrationRequest;
import home.tm.TimeKeeper.api.models.UserDto;
import home.tm.security.jwt.JwtUtil;
import home.tm.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController implements UsersApi {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    public ResponseEntity<AuthResponse> login(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        String token = jwtUtil.generateToken(authRequest.getUsername());


        return ResponseEntity.ok(new AuthResponse(token));
    }

    @Override
    public ResponseEntity<UserDto> registration(RegistrationRequest registrationRequest) {
        return new ResponseEntity<>(userService.register(registrationRequest), HttpStatus.OK);
    }
}
