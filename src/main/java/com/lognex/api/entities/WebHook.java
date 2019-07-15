package com.lognex.api.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class WebHook {
    private String entityType;
    private String url;
    private HttpMethod method;
    private Boolean enabled;
    private EntityAction action;

    public enum HttpMethod {
        POST
    }

    public enum EntityAction {
        CREATE,
        UPDATE,
        DELETE
    }
}
