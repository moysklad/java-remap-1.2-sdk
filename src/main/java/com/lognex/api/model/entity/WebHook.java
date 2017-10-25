package com.lognex.api.model.entity;

import com.lognex.api.model.base.field.EntityAction;
import com.lognex.api.model.base.field.HttpMethod;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WebHook {

    private String entityType;
    private String url;
    private HttpMethod method = HttpMethod.POST;
    private boolean enabled;
    private EntityAction action;
}
