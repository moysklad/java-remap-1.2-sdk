package com.lognex.api.exception;

import com.lognex.api.response.ApiError;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class ResponseException extends RuntimeException {
    @Getter
    private Set<ApiError> errors = new HashSet<>();
}
