package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.WebHook;

public final class WebHookClient
        extends EntityClientBase
        implements
        GetListEndpoint<WebHook>,
        PostEndpoint<WebHook>,
        DeleteByIdEndpoint,
        GetByIdEndpoint<WebHook>,
        PutByIdEndpoint<WebHook>,
        MassCreateUpdateDeleteEndpoint<WebHook> {

    public WebHookClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/webhook/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return WebHook.class;
    }
}
