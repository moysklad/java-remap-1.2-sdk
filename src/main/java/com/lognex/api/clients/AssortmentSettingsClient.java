package com.lognex.api.clients;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.endpoints.GetEndpoint;
import com.lognex.api.clients.endpoints.PutEndpoint;
import com.lognex.api.entities.AssortmentSettings;
import com.lognex.api.entities.MetaEntity;

public final class AssortmentSettingsClient
            extends EntityClientBase
            implements GetEndpoint<AssortmentSettings>, PutEndpoint<AssortmentSettings> {

    public AssortmentSettingsClient(ApiClient api) {
        super(api, "/entity/assortment/settings/");
    }

        @Override
        public Class<? extends MetaEntity> entityClass() {
        return AssortmentSettings.class;
    }
}
