package home.tm.exceptions;

import org.springframework.http.HttpStatus;

import static home.tm.exceptions.ExceptionType.CHYBA_AUTH;


public class AuthException extends TimeKeeperException {

    public AuthException(String message) {
        super(CHYBA_AUTH, message, HttpStatus.FORBIDDEN, SeverityEnum.ERROR);
    }
}
