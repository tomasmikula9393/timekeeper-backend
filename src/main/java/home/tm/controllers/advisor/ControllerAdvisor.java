package home.tm.controllers.advisor;


import home.tm.exceptions.AuthException;
import home.tm.exceptions.ErrorConverter;
import home.tm.exceptions.ExceptionMessage;
import home.tm.exceptions.InternalException;
import home.tm.exceptions.ResponseErrorDto;
import home.tm.exceptions.SeverityEnum;
import home.tm.exceptions.TimeKeeperException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static home.tm.exceptions.ExceptionType.INTERNI_CHYBA;

@ControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    private final ErrorConverter errorConverter;

    @ExceptionHandler(TimeKeeperException.class)
    public ResponseEntity<ResponseErrorDto> handleApplicationException(
            TimeKeeperException exception, WebRequest request) {
        return new ResponseEntity<>(errorConverter.toDto(exception), exception.getStatus());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ResponseErrorDto> handleInternalApplicationThrowable(
            Throwable throwable,
            WebRequest request) {
        logger.error(throwable.getMessage(), throwable);
        if (throwable instanceof AccessDeniedException) {
            return handleApplicationException(
                    new AuthException(String.format(ExceptionMessage.USER_IS_NOT_AUTHORIZED.getMessage(), request.getUserPrincipal().getName())),
                    request
            );
        } else {
            return handleApplicationException(
                    new InternalException(INTERNI_CHYBA, throwable.getMessage(), SeverityEnum.ERROR),
                    request);
        }
    }
}
