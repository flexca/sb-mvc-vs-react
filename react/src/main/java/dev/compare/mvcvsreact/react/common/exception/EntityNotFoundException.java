package dev.compare.mvcvsreact.react.common.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends MvcVsReactException {

    public EntityNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(HttpStatus.NOT_FOUND, message, cause);
    }

}

