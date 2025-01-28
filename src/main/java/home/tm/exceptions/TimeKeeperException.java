package home.tm.exceptions;


import lombok.Getter;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public abstract class TimeKeeperException extends RuntimeException {

    private final ExceptionType id;
    private final String casVzniku;
    private final String chyboveHlaseni;
    private final String idTransakce;
    private final String idSession;
    private final String detaily;
    private final HttpStatus status;
    private final SeverityEnum severity;
    private Long typEntityId;
    private Long entityId;
    private String auditMessage;

    protected TimeKeeperException(ExceptionType id, String message, HttpStatus status, SeverityEnum severity) {
        this.id = id;
        this.casVzniku = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
        this.chyboveHlaseni = message;
        this.idTransakce = MDC.get(HeaderConstants.TRANSACTION_ID_HEADER_NAME);
        this.idSession = MDC.get(HeaderConstants.GLOBAL_ID_COOKIE_NAME);
        this.detaily = getBriefStackTrace(this);
        this.status = status;
        this.severity = severity;
    }

    protected TimeKeeperException(ExceptionType id, String message, String detaily, HttpStatus status, SeverityEnum severity, String auditMessage) {
        this.id = id;
        this.casVzniku = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
        this.chyboveHlaseni = message;
        this.idTransakce = MDC.get(HeaderConstants.TRANSACTION_ID_HEADER_NAME);
        this.idSession = MDC.get(HeaderConstants.GLOBAL_ID_COOKIE_NAME);
        this.detaily = detaily;
        this.status = status;
        this.severity = severity;
        this.auditMessage = auditMessage;
    }

    protected TimeKeeperException(ExceptionType id, String message, HttpStatus status, SeverityEnum severity, String auditMessage, Long typEntityId, Long entityId) {
        this(id, message, status, severity);
        this.auditMessage = auditMessage;
        this.typEntityId = typEntityId;
        this.entityId = entityId;
    }

    protected TimeKeeperException(ExceptionType id, String message, HttpStatus status, SeverityEnum severity, String auditMessage) {
        this(id, message, status, severity);
        this.auditMessage = auditMessage;
    }

    public static String getBriefStackTrace(Throwable throwable) {
        if (throwable instanceof TimeKeeperException) {
            for (int i = 1; i < throwable.getStackTrace().length; i++) {
                final var stackElement = throwable.getStackTrace()[i];
                if (stackElement.getClassName().contains("cz.legend.eisir") && !stackElement.getMethodName().equals("newTimeKeeperException")) {
                    return getBriefDetails(throwable, stackElement);
                }
            }
        }
        return throwable.toString();
    }

    private static @NotNull String getBriefDetails(Throwable throwable, StackTraceElement stackElement) {
        final var PREFIX = "class ";
        final var exceptionClassName = throwable.getClass().toString().contains(PREFIX)
                ? throwable.getClass().toString().substring(PREFIX.length()) : throwable.getClass().toString();
        return exceptionClassName + ": " + stackElement.getClassName() +
                "." + stackElement.getMethodName() + ": " + stackElement.getLineNumber();
    }
}