package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.endpoints.ApiChainElement;
import ru.moysklad.remap_1_2.clients.endpoints.GetEndpoint;
import ru.moysklad.remap_1_2.clients.endpoints.MetadataEndpoint;
import ru.moysklad.remap_1_2.clients.endpoints.PutEndpoint;
import ru.moysklad.remap_1_2.entities.CompanySettings;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.responses.metadata.CompanySettingsMetadata;

public final class CompanySettingsClient
        extends EntityClientBase
        implements GetEndpoint<CompanySettings>,
        PutEndpoint<CompanySettings>,
        MetadataEndpoint<CompanySettingsMetadata> {

    public CompanySettingsClient(ApiClient api) {
        super(api, "/context/companysettings/");
    }

    @ApiChainElement
    public PriceTypeClient pricetype() {
        return new PriceTypeClient(api(), path());
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return CompanySettings.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return CompanySettingsMetadata.class;
    }
}
