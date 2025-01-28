package home.tm.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends TimeKeeperException {

    public NotFoundException(ExceptionType type, String message, SeverityEnum severity) {
        super(type, message, HttpStatus.NOT_FOUND, severity);
    }

    public NotFoundException(ExceptionType type, String message, SeverityEnum severity, String auditMessage, Long typEntityId, Long entityId) {
        super(type, message, HttpStatus.NOT_FOUND, severity, auditMessage, typEntityId, entityId);
    }

    public NotFoundException(ExceptionType type, String message, SeverityEnum severity, String auditMessage) {
        super(type, message, HttpStatus.NOT_FOUND, severity, auditMessage);
    }
}
