package com.dimevision.redmadrobots.exception.handler;

import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;

import static org.springframework.http.HttpStatus.*;

/**
 * @author Dimevision
 * @version 0.1
 * <p>
 * Обработчик ошибок, связанных с некорректной отправкой запросов
 */

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildResponseEntity(new ApiError(BAD_REQUEST, ex.getMessage(), ex.getClass().getSimpleName(), ex));
    }

    @Override

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildResponseEntity(new ApiError(INTERNAL_SERVER_ERROR, ex.getMessage(), ex.getClass().getSimpleName(), ex));
    }

    @ExceptionHandler({BadCredentialsException.class})
    protected ResponseEntity<Object> handleBadCredentials(BadCredentialsException ex) {
        ApiError errorHandler = new ApiError(UNAUTHORIZED, ex.getMessage(), ex.getClass().getSimpleName(), ex);

        return buildResponseEntity(errorHandler);
    }

    @ExceptionHandler({SQLException.class})
    protected ResponseEntity<Object> handleSqlException(SQLException ex) {
        ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR, ex.getMessage(), ex.getClass().getSimpleName(), ex);

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<Object> handleEntityNotFound(IllegalArgumentException ex) {
        ApiError errorHandler = new ApiError(NOT_FOUND, ex.getMessage(), ex.getClass().getSimpleName(), ex);

        return buildResponseEntity(errorHandler);
    }

    @ExceptionHandler({TokenExpiredException.class})
    protected ResponseEntity<Object> handleTokenExpired(TokenExpiredException ex) {
        ApiError errorHandler = new ApiError(FORBIDDEN, ex.getMessage(), ex.getClass().getSimpleName(), ex);

        return buildResponseEntity(errorHandler);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        ApiError errorHandler = new ApiError(NOT_FOUND, ex.getMessage(), ex.getClass().getSimpleName(), ex);

        return buildResponseEntity(errorHandler);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}