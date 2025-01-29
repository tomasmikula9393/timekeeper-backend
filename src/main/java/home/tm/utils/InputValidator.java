package home.tm.utils;

import home.tm.TimeKeeper.api.models.ItemDto;
import home.tm.TimeKeeper.api.models.RegistrationRequest;
import home.tm.exceptions.InternalException;
import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static home.tm.exceptions.ExceptionMessage.MISSING_PARAMS;
import static home.tm.exceptions.ExceptionType.BUSINESS_CHYBA;
import static home.tm.exceptions.SeverityEnum.ERROR;

@UtilityClass
public class InputValidator {

    public static void validateRegistration(RegistrationRequest registrationRequest) {
        List<String> missingFields = new ArrayList<>();
        if (registrationRequest.getEmail() == null) {
            missingFields.add("email");
        }
        if (registrationRequest.getUsername() == null) {
            missingFields.add("přihlašovací jméno");
        }
        if (registrationRequest.getPassword() == null) {
            missingFields.add("heslo");
        }
        if (!CollectionUtils.isEmpty(missingFields)) {
            throw new InternalException(BUSINESS_CHYBA, String.format(MISSING_PARAMS.getMessage(), missingFields), ERROR);
        }
    }

    public static void validateItem(ItemDto itemDto) {
        List<String> missingFields = new ArrayList<>();
        if (itemDto.getValidityTo() == null) {
            missingFields.add("platnost do");
        }
        if (itemDto.getTitle() == null) {
            missingFields.add("název");
        }
        if (!CollectionUtils.isEmpty(missingFields)) {
            throw new InternalException(BUSINESS_CHYBA, String.format(MISSING_PARAMS.getMessage(), missingFields), ERROR);
        }
    }
}
