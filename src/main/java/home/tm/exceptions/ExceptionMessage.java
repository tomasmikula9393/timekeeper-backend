package home.tm.exceptions;

import lombok.Getter;

@Getter
public enum ExceptionMessage {
    BODY_MEASUREMENT_WAS_NOT_FOUND("Tělesné měření s id %d nebylo nalezeno"),
    USER_IS_NOT_AUTHORIZED("Uživatel s id %d není pro tuto akci oprávněn"),
    USER_ALREADY_EXISTS("Uživatel s [email] nebo [username] již existuje"),
    USER_WAS_NOT_FOUND("Uživatel s [id]: %d nebyl nalezen"),
    ITEM_WAS_NOT_FOUND("Položka s %d nebyla nalezena"),
    TASK_WAS_NOT_FOUND("Úkol s %d nebyl nalezen"),
    TRAINING_WAS_NOT_FOUND("Trénink s id %d nebyl nalezen"),
    TRAINING_DIARY_WAS_NOT_FOUND("Tréninkový deník s id %d nebyl nalezen"),
    MISSING_PARAMS("Nebyly vyplněny povinné parametry [%s]"),
    ;

    private final String message;


    ExceptionMessage(String message) {
        this.message = message;
    }
}
