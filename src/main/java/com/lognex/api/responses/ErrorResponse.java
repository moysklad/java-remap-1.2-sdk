package com.lognex.api.responses;

import java.util.List;

/**
 * Ответ сервера с ошибкой
 */
public class ErrorResponse {
    public List<Error> errors;

    public static class Error {
        public String error;
        public Integer code;
        public String parameter;
        public String moreInfo;
        public Integer line;
        public Integer column;
    }
}
