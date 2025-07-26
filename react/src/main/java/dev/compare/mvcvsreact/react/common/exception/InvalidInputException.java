package dev.compare.mvcvsreact.react.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidInputException extends MvcVsReactException {

    public InvalidInputException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public InvalidInputException(String message, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, message, cause);
    }
}
