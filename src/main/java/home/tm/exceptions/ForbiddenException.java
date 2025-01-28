package home.tm.exceptions;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends TimeKeeperException {

    public ForbiddenException(ExceptionType type, String message, SeverityEnum severity) {
        super(type, message, HttpStatus.FORBIDDEN, severity);
    }

    public ForbiddenException(ExceptionType type, String message, SeverityEnum severity, String auditMessage, Long typEntityId, Long entityId) {
        super(type, message, HttpStatus.FORBIDDEN, severity, auditMessage, typEntityId, entityId);
    }

    public ForbiddenException(ExceptionType type, String message, SeverityEnum severity, String auditMessage) {
        super(type, message, HttpStatus.FORBIDDEN, severity, auditMessage);
    }
}