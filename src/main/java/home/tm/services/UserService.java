package home.tm.services;

import home.tm.TimeKeeper.api.models.RegistrationRequest;
import home.tm.TimeKeeper.api.models.UserDto;

public interface UserService {
    UserDto register(RegistrationRequest registrationRequest);
}
