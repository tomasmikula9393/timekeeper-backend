package home.tm.exceptions;

import org.springframework.http.HttpStatus;

public class DataIntegrityException extends TimeKeeperException {

    public DataIntegrityException(ExceptionType exceptionType, String message, SeverityEnum severity) {
        super(exceptionType, message, HttpStatus.INTERNAL_SERVER_ERROR, severity);
    }
}
