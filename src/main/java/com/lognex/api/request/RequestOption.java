package com.lognex.api.request;

public enum RequestOption {
    DATE_TIME_WITH_MILLISECONDS("X-Lognex-Format-Millisecond", "true"),
    JSON_PRETTY_PRINT("Lognex-Pretty-Print-JSON", "true"),
    OPERATION_PRICES_WITHOUT_ROUNDING("X-Lognex-Precision", "true"),
    WITHOUT_WEBHOOK_TRIGGER("X-Lognex-WebHook-Disable", "true"),
    PRINT_DOCUMENT_CONTENT("X-Lognex-Get-Content", "true");

    private String header;
    private String value;

    RequestOption(String headerName, String headerValue){
        this.header = headerName;
        this.value = headerValue;
    }

    public String getHeader() {
        return header;
    }

    public String getValue() {
        return value;
    }
}
