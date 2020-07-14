package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.products.Variant;
import com.lognex.api.responses.metadata.VariantMetadataResponse;

public final class VariantClient
        extends EntityClientBase
        implements
        GetListEndpoint<Variant>,
        PostEndpoint<Variant>,
        DeleteByIdEndpoint,
        MetadataEndpoint<VariantMetadataResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<Variant>,
        PutByIdEndpoint<Variant>,
        MassCreateUpdateDeleteEndpoint<Variant>,
        HasImagesEndpoint<Variant> {

    public VariantClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/variant/");
    }

    public CharacteristicClient characteristics() {
        return new CharacteristicClient(api());
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Variant.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return VariantMetadataResponse.class;
    }
}