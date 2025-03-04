package com.test.api.marvel.exception;

public record ApiErrorDto(
        String message,
        String backMessage,
        String method,
        String path
) {
}
