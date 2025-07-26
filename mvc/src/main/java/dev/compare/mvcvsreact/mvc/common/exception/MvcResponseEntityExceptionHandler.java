package dev.compare.mvcvsreact.mvc.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class MvcResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { MvcVsReactException.class })
    protected ResponseEntity<Object> handleMvcVsReactException(MvcVsReactException mvcVsReactException, WebRequest request) {
        log.error("Error: {}", mvcVsReactException.getMessage(), mvcVsReactException);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        return handleExceptionInternal(mvcVsReactException, new ErrorResponseDto(mvcVsReactException.getMessage()), headers,
                mvcVsReactException.getHttpStatusCode(), request);
    }

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleGenericException(Exception exception, WebRequest request) {
        log.error("Error: {}", exception.getMessage(), exception);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        return handleExceptionInternal(exception, new ErrorResponseDto(exception.getMessage()), headers,
                HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
