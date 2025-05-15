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
    private String stockType;
    private String reportType;
    private String url;
    private HttpMethod method;
    private Boolean enabled;

    public enum HttpMethod {
        POST
    }

    public enum StockType {
        STOCK("stock"),
        ;

        private final String name;

        StockType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public enum ReportType {
        ALL("all"),
        BY_STORE("bystore"),
        ;
        private final String name;

        ReportType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
