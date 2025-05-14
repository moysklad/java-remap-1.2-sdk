package ru.moysklad.remap_1_2.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WebHookStock extends MetaEntity {
    private StockType stockType;
    private ReportType reportType;
    private String url;
    private HttpMethod method;
    private Boolean enabled;

    public enum HttpMethod {
        POST
    }

    public enum StockType {
        STOCK
    }

    public enum ReportType {
        ALL,
        BY_STORE
    }
}
