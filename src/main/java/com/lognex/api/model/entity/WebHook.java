package com.lognex.api.model.entity;

import com.lognex.api.model.entity.item.EntityAction;
import com.lognex.api.model.entity.item.HttpMethod;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WebHook {

    private String entityType; //todo тип сущности, к которой привязан веб-хук Необходимое
    private String url;
    private HttpMethod method = HttpMethod.POST;
    private boolean enabled;
    private EntityAction action;
}
