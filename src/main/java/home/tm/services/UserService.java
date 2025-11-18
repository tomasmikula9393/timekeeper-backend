package home.tm.services;

import home.tm.TimeKeeper.api.models.RegistrationRequest;
import home.tm.TimeKeeper.api.models.UserDto;
import home.tm.model.User;
import home.tm.model.UserProfile;

import java.util.List;

public interface UserService {
    UserDto register(RegistrationRequest registrationRequest);

    UserProfile getUserProfile(Long userId);

    List<User> getAllUsers();
}
