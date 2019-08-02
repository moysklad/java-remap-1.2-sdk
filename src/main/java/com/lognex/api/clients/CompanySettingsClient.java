package com.lognex.api.clients;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.endpoints.ApiChainElement;
import com.lognex.api.clients.endpoints.GetEndpoint;
import com.lognex.api.clients.endpoints.MetadataEndpoint;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.CompanySettingsResponse;
import com.lognex.api.responses.metadata.CompanySettingsMetadata;

public final class CompanySettingsClient
        extends EntityClientBase
        implements GetEndpoint<CompanySettingsResponse>,
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
        return CompanySettingsResponse.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return CompanySettingsMetadata.class;
    }
}
