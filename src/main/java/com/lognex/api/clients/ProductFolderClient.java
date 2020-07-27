package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.ProductFolder;
import com.lognex.api.responses.metadata.MetadataAttributeResponse;

public final class ProductFolderClient
        extends EntityClientBase
        implements
        GetListEndpoint<ProductFolder>,
        PostEndpoint<ProductFolder>,
        DeleteByIdEndpoint,
        MetadataEndpoint<MetadataAttributeResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<ProductFolder>,
        PutByIdEndpoint<ProductFolder>,
        MassCreateUpdateDeleteEndpoint<ProductFolder> {

    public ProductFolderClient(com.lognex.api.ApiClient api) {
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
