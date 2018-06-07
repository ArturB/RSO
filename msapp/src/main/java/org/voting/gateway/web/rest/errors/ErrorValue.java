package org.voting.gateway.web.rest.errors;

/**
 * Created by defacto on 6/7/2018.
 */
public class ErrorValue {
    private int index;
    private String message;

    private ErrorValue(int index, String message) {
        this.index = index;
        this.message = message;
    }

    public int getIndex() {
        return index;
    }

    public String getMessage() {
        return message;
    }

    public static ErrorValue USER_NOT_FOUND = new ErrorValue(1, "Nie znaleziono użytkownika o podanym id: {0}");
    public static ErrorValue USER_ARLEADY_DISABLED = new ErrorValue(2, "Użytkownik już jest zablokowany");
    public static ErrorValue MUNICIPALITY_NOT_FOUND= new ErrorValue(3, "Nie znaleziono gminy o id {0}");
}