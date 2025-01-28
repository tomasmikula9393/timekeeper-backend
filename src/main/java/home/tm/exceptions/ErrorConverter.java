package home.tm.exceptions;

import org.springframework.stereotype.Component;

@Component
public class ErrorConverter {

    public ResponseErrorDto toDto(TimeKeeperException exception) {
        return new ResponseErrorDto(exception);
    }
}