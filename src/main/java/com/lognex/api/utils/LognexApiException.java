package com.lognex.api.utils;

import com.lognex.api.responses.ErrorResponse;
import lombok.Getter;

import java.util.stream.Collectors;

@Getter
public class LognexApiException extends Exception {
    private final int statusCode;
    private final String reasonPhrase;
    private final ErrorResponse errorResponse;

    public LognexApiException(int statusCode, String reasonPhrase) {
        super(statusCode + " " + reasonPhrase);

        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.errorResponse = null;
    }

    public LognexApiException(int statusCode, String reasonPhrase, ErrorResponse er) {
        super(
                er.getErrors().isEmpty() ?
                        statusCode + " " + reasonPhrase :
                        "При запросе произошли следующие ошибки (" + statusCode + " " + reasonPhrase + "):\n" +
                                er.getErrors().stream().map(e -> " - " + e.getCode() + " " + e.getError()).
                                        collect(Collectors.joining("\n"))
        );

        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.errorResponse = er;
    }
}
