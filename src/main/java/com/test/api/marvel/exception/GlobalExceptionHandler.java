package com.test.api.marvel.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDto> handlerGeneralExceptions(Exception e, HttpServletRequest request, WebRequest webRequest) {
        e.printStackTrace();
        if (e instanceof HttpClientErrorException) {
            return this.handleHttpClientErrorException((HttpClientErrorException) e, request, webRequest);
        } else if (e instanceof AccessDeniedException) {
            return this.handleAccessDeniedException((AccessDeniedException) e, request, webRequest);
        } else if (e instanceof AuthenticationCredentialsNotFoundException) {
            return this.handleAuthenticationCredentialsNotFoundException((AuthenticationCredentialsNotFoundException) e, request, webRequest);
        } else {
            return this.handleGenericException(e, request, webRequest);
        }

    }

    private ResponseEntity<ApiErrorDto> handleGenericException(
            Exception e,
            HttpServletRequest request,
            WebRequest webRequest) {

        ApiErrorDto dto = new ApiErrorDto(
                "Error inesperado vuelva a intentarlo",
                e.getMessage(),
                request.getMethod(),
                request.getRequestURL().toString()
        );

        return ResponseEntity.internalServerError().body(dto);

    }

    private ResponseEntity<ApiErrorDto> handleAuthenticationCredentialsNotFoundException(
            AuthenticationCredentialsNotFoundException e,
            HttpServletRequest request,
            WebRequest webRequest) {

        ApiErrorDto dto = new ApiErrorDto(
                "No tienes acceso a este recurso",
                e.getMessage(),
                request.getMethod(),
                request.getRequestURL().toString()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(dto);
    }

    private ResponseEntity<ApiErrorDto> handleAccessDeniedException(
            AccessDeniedException e,
            HttpServletRequest request,
            WebRequest webRequest) {

        ApiErrorDto dto = new ApiErrorDto(
                "No tienes acceso a este recurso",
                e.getMessage(),
                request.getMethod(),
                request.getRequestURL().toString()
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(dto);
    }

    private ResponseEntity<ApiErrorDto> handleHttpClientErrorException(
            HttpClientErrorException e,
            HttpServletRequest request,
            WebRequest webRequest) {

        String message = null;

        if (e instanceof HttpClientErrorException.Forbidden) {
            message = "No tienes acceso a este recurso";
        } else if (e instanceof HttpClientErrorException.Unauthorized) {
            message = "No haz iniciado sessión para acceder a este recurso";
        } else if (e instanceof HttpClientErrorException.NotFound) {
            message = "Recurso no encontrado";
        } else if (e instanceof HttpClientErrorException.Conflict) {
            message = "Conflicto en el procesamiento de la petición";
        } else {
            message = "Error inesperado al realizar consulta";
        }

        ApiErrorDto dto = new ApiErrorDto(
                message,
                e.getMessage(),
                request.getMethod(),
                request.getRequestURL().toString()
        );

        return ResponseEntity.status(e.getStatusCode()).body(dto);
    }

}