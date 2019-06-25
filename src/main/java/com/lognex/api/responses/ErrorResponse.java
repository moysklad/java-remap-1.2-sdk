package com.lognex.api.responses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Ответ сервера с ошибкой
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ErrorResponse {
    private List<Error> errors;

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class Error {
        private String error;
        private Integer code;
        private String parameter;
        private String moreInfo;
        private Integer line;
        private Integer column;
    }
}
