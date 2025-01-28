package home.tm.services.impl;

import home.tm.TimeKeeper.api.models.RegistrationRequest;
import home.tm.TimeKeeper.api.models.UserDto;
import home.tm.converters.UserConverter;
import home.tm.model.User;
import home.tm.repositories.UserRepository;
import home.tm.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    public UserDto register(RegistrationRequest registrationRequest) {
        if (userRepository.findByUsername(registrationRequest.getUsername()).isPresent()) {
//            throw new Exception()
        }
        User user = userRepository.save(userConverter.toEntity(registrationRequest));
        return userConverter.toDto(user);
    }
}
