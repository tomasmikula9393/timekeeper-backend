package home.tm.services.impl;

import home.tm.TimeKeeper.api.models.RegistrationRequest;
import home.tm.TimeKeeper.api.models.UserDto;
import home.tm.converters.UserConverter;
import home.tm.exceptions.ExceptionMessage;
import home.tm.exceptions.ForbiddenException;
import home.tm.exceptions.NotFoundException;
import home.tm.model.User;
import home.tm.model.UserProfile;
import home.tm.repositories.UserProfileRepository;
import home.tm.repositories.UserRepository;
import home.tm.services.UserService;
import home.tm.utils.InputValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static home.tm.exceptions.ExceptionType.CHYBNY_VSTUP;
import static home.tm.exceptions.ExceptionType.NEBYLO_NALEZENO;
import static home.tm.exceptions.SeverityEnum.ERROR;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final UserProfileRepository userProfileRepository;

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

    @Override
    public UserProfile getUserProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(NEBYLO_NALEZENO, String.format(ExceptionMessage.USER_WAS_NOT_FOUND.getMessage(), userId), ERROR)
        );
        return userProfileRepository.findByUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
