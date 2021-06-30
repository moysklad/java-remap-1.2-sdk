package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.products.Product;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedPriceTypesResponse;

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
        GetBySyncIdEndpoint<Product>,
        HasFilesEndpoint<Product> {

    public ProductClient(ru.moysklad.remap_1_2.ApiClient api) {
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
