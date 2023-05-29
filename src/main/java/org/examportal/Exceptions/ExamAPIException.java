package org.examportal.Exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;


public class ExamAPIException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public ExamAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
    public ExamAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
