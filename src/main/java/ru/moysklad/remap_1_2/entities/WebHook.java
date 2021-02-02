package ru.moysklad.remap_1_2.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WebHook extends MetaEntity {
    private Meta.Type entityType;
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
