package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.WebHookStock;

public class WebHookStockClient
        extends EntityClientBase
        implements
        GetListEndpoint<WebHookStock>,
        PostEndpoint<WebHookStock>,
        DeleteByIdEndpoint,
        GetByIdEndpoint<WebHookStock>,
        PutByIdEndpoint<WebHookStock>,
        MassCreateUpdateDeleteEndpoint<WebHookStock> {

    public WebHookStockClient(ru.moysklad.remap_1_2.ApiClient api) {
            super(api, "/entity/webhookstock/");
        }

        @Override
        public Class<? extends MetaEntity> entityClass() {
            return WebHookStock.class;
        }
}
