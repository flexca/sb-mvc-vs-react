package dev.compare.mvcvsreact.react.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

public class MvcVsReactException extends RuntimeException {

    @Getter
    private final HttpStatusCode httpStatusCode;

    protected MvcVsReactException(HttpStatusCode httpStatusCode, String message) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }

    protected MvcVsReactException(HttpStatusCode httpStatusCode, String message, Throwable cause) {
        super(message, cause);
        this.httpStatusCode = httpStatusCode;
    }
}
