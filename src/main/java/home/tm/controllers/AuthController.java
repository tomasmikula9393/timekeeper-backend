package home.tm.controllers;

import home.tm.TimeKeeper.api.controllers.UsersApi;
import home.tm.TimeKeeper.api.models.AuthRequest;
import home.tm.TimeKeeper.api.models.AuthResponse;
import home.tm.TimeKeeper.api.models.RegistrationRequest;
import home.tm.TimeKeeper.api.models.UserDto;
import home.tm.security.jwt.JwtUtil;
import home.tm.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

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

        // **Získání `HttpServletResponse` z kontextu**
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attr != null) {
            HttpServletResponse response = attr.getResponse();
            if (response != null) {
                ResponseCookie cookie = ResponseCookie.from("authToken", token)
                        .httpOnly(true)
                        .secure(true) // Pouze pro HTTPS
                        .path("/")
                        .sameSite("None") // Pro cross-origin požadavky
                        .build();

                response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            }
        }

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @Override
    public ResponseEntity<UserDto> registration(RegistrationRequest registrationRequest) {
        return new ResponseEntity<>(userService.register(registrationRequest), HttpStatus.OK);
    }
}
