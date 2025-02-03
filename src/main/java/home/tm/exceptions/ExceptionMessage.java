package home.tm.exceptions;

import lombok.Getter;

@Getter
public enum ExceptionMessage {
    USER_IS_NOT_AUTHORIZED("Uživatel s id %d není pro tuto akci oprávněn"),
    USER_ALREADY_EXISTS("Uživatel s [email] nebo [username] již existuje"),
    ITEM_WAS_NOT_FOUND("Položka s %d nebyla nalezena"),
    TASK_WAS_NOT_FOUND("Úkol s %d nebyl nalezen"),
    MISSING_PARAMS("Nebyly vyplněny povinné parametry [%s]"),
    ;

    private final String message;


    ExceptionMessage(String message) {
        this.message = message;
    }
}
