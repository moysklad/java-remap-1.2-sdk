package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.products.ProductEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedPriceTypesResponse;

public final class ProductClient
        extends ApiClient
        implements
        GetListEndpoint<ProductEntity>,
        PostEndpoint<ProductEntity>,
        DeleteByIdEndpoint,
        MetadataEndpoint<MetadataAttributeSharedPriceTypesResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<ProductEntity>,
        PutByIdEndpoint<ProductEntity> {

    public ProductClient(LognexApi api) {
        super(api, "/entity/product/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return ProductEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedPriceTypesResponse.class;
    }
}
