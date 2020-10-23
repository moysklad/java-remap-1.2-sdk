package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.products.Product;
import com.lognex.api.responses.metadata.MetadataAttributeSharedPriceTypesResponse;

public final class ProductClient
        extends EntityClientBase
        implements
        GetListEndpoint<Product>,
        PostEndpoint<Product>,
        DeleteByIdEndpoint,
        MetadataEndpoint<MetadataAttributeSharedPriceTypesResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<Product>,
        PutByIdEndpoint<Product>,
        MassCreateUpdateDeleteEndpoint<Product>,
        HasImagesEndpoint<Product>,
        GetBySyncIdEndpoint<Product> {

    public ProductClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/product/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Product.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedPriceTypesResponse.class;
    }

}
