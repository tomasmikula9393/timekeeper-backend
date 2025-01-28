package home.tm.exceptions;

import org.springframework.http.HttpStatus;

public class InternalException extends TimeKeeperException {

    public InternalException(ExceptionType exceptionType, String message, SeverityEnum severity) {
        super(exceptionType, message == null ? "Interní chyba systému, zkuste prosím akci opakovat později" : message, HttpStatus.INTERNAL_SERVER_ERROR, severity);
    }

    public InternalException(ExceptionType exceptionType, String message, SeverityEnum severity, String auditMessage) {
        super(exceptionType, message == null ? "Interní chyba systému, zkuste prosím akci opakovat později" : message, HttpStatus.INTERNAL_SERVER_ERROR, severity, auditMessage);
    }

    public InternalException(ExceptionType exceptionType, String message, String detaily, HttpStatus status, SeverityEnum severityEnum, String auditMessage) {
        super(exceptionType, message, detaily, status, severityEnum, auditMessage);
    }
}
