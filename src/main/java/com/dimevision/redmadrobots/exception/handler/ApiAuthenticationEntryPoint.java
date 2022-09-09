package com.dimevision.redmadrobots.exception.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dimevision
 * @version 0.1
 */

@Data
public class ApiAuthenticationEntryPoint implements AuthenticationEntryPoint, AccessDeniedHandler {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        buildResponse(401, "Необходимо выполнить вход в систему", authException.getClass().getSimpleName(), response);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        buildResponse(403, accessDeniedException.getMessage(), accessDeniedException.getClass().getSimpleName(), response);
    }

    private void buildResponse(int statusCode, String reason, String exception, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(statusCode);

        Map<String, Object> responseBody = new HashMap<>();
        timestamp = Date.from(Instant.now(Clock.systemUTC()));
        responseBody.put("Timestamp", timestamp.toString());
        responseBody.put("Reason", reason);
        responseBody.put("Exception", exception);
        new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
    }
}
