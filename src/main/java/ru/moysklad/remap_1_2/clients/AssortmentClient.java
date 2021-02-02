package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.clients.endpoints.GetListEndpoint;
import ru.moysklad.remap_1_2.clients.endpoints.HasSettingsEndpoint;
import ru.moysklad.remap_1_2.entities.Assortment;
import ru.moysklad.remap_1_2.entities.AssortmentSettings;
import ru.moysklad.remap_1_2.entities.MetaEntity;

public final class AssortmentClient
        extends EntityClientBase
        implements
        GetListEndpoint<Assortment>,
        HasSettingsEndpoint<AssortmentSettings> {

    public AssortmentClient(ru.moysklad.remap_1_2.ApiClient api) {
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
