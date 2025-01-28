package home.tm.exceptions;

import lombok.Getter;

@Getter
public enum ExceptionMessage {
    USER_IS_NOT_AUTHORIZED("User with id %d is not authorized for this action"),
    ITEM_WAS_NOT_FOUND("Item with id %d was not found"),
    ;

    private final String message;


    ExceptionMessage(String message) {
        this.message = message;
    }
}
