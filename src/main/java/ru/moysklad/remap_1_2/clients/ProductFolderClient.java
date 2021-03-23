package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.ProductFolder;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeResponse;

public final class ProductFolderClient
        extends EntityClientBase
        implements
        GetListEndpoint<ProductFolder>,
        PostEndpoint<ProductFolder>,
        DeleteByIdEndpoint,
        MetadataEndpoint<MetadataAttributeResponse>,
        GetByIdEndpoint<ProductFolder>,
        PutByIdEndpoint<ProductFolder>,
        MassCreateUpdateDeleteEndpoint<ProductFolder> {

    public ProductFolderClient(ru.moysklad.remap_1_2.ApiClient api) {
        super(api, "/entity/productfolder/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return ProductFolder.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeResponse.class;
    }
}
