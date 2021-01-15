package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.WebHook;

public final class WebHookClient
        extends EntityClientBase
        implements
        GetListEndpoint<WebHook>,
        PostEndpoint<WebHook>,
        DeleteByIdEndpoint,
        GetByIdEndpoint<WebHook>,
        PutByIdEndpoint<WebHook>,
        MassCreateUpdateDeleteEndpoint<WebHook> {

    public WebHookClient(ru.moysklad.remap_1_2.ApiClient api) {
        super(api, "/entity/webhook/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return WebHook.class;
    }
}
