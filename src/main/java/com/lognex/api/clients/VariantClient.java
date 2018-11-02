package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.products.VariantEntity;
import com.lognex.api.responses.metadata.VariantMetadataResponse;

public final class VariantClient
        extends ApiClient
        implements
        GetListEndpoint<VariantEntity>,
        PostEndpoint<VariantEntity>,
        DeleteByIdEndpoint,
        MetadataEndpoint<VariantMetadataResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<VariantEntity>,
        PutByIdEndpoint<VariantEntity> {

    public VariantClient(LognexApi api) {
        super(api, "/entity/variant/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return VariantEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return VariantMetadataResponse.class;
    }
}