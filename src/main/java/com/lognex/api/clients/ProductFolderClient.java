package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.ProductFolderEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedResponse;

public final class ProductFolderClient
        extends ApiClient
        implements
        GetListEndpoint<ProductFolderEntity>,
        PostEndpoint<ProductFolderEntity>,
        PutByIdEndpoint<ProductFolderEntity>,
        DeleteByIdEndpoint,
        MetadataEndpoint<MetadataAttributeSharedResponse>,
        MetadataAttributeEndpoint {

    public ProductFolderClient(LognexApi api) {
        super(api, "/entity/productfolder/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return ProductFolderEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedResponse.class;
    }
}
