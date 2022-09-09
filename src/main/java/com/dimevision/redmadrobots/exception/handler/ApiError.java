package com.dimevision.redmadrobots.exception.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * @author Dimevision
 * @version 0.1
 */

@Data
public class ApiError {

    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private String reason;

    private String exception;

    public ApiError(HttpStatus status) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.reason = "Unexpected error";
    }

    public ApiError(HttpStatus status, String reason, String exception, Throwable ex) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.reason = reason;
        this.exception = exception;
    }
}
