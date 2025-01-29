package home.tm.services.impl;

import home.tm.TimeKeeper.api.models.RegistrationRequest;
import home.tm.TimeKeeper.api.models.UserDto;
import home.tm.converters.UserConverter;
import home.tm.exceptions.ExceptionMessage;
import home.tm.exceptions.ForbiddenException;
import home.tm.model.User;
import home.tm.repositories.UserRepository;
import home.tm.services.UserService;
import home.tm.utils.InputValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static home.tm.exceptions.ExceptionType.CHYBNY_VSTUP;
import static home.tm.exceptions.SeverityEnum.ERROR;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    public UserDto register(RegistrationRequest registrationRequest) {
        InputValidator.validateRegistration(registrationRequest);
        if (userRepository.findByUsername(registrationRequest.getUsername()).isPresent() ||
                userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
            throw new ForbiddenException(CHYBNY_VSTUP, String.format(ExceptionMessage.USER_ALREADY_EXISTS.getMessage()), ERROR);
        }
        User user = userRepository.save(userConverter.toEntity(registrationRequest));
        return userConverter.toDto(user);
    }
}
