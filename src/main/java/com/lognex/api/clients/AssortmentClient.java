package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.HasSettingsEndpoint;
import com.lognex.api.entities.Assortment;
import com.lognex.api.entities.AssortmentSettings;
import com.lognex.api.entities.MetaEntity;

public final class AssortmentClient
        extends EntityClientBase
        implements
        GetListEndpoint<Assortment>,
        HasSettingsEndpoint<AssortmentSettings> {

    public AssortmentClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/assortment/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Assortment.class;
    }

    @Override
    public Class<AssortmentSettings> settingsEntityClass() {
        return AssortmentSettings.class;
    }
}
