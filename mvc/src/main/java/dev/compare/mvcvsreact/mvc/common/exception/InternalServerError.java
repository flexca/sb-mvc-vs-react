package dev.compare.mvcvsreact.mvc.common.exception;

import org.springframework.http.HttpStatus;

public class InternalServerError extends MvcVsReactException {

    public InternalServerError(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public InternalServerError(String message, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message, cause);
    }
}
