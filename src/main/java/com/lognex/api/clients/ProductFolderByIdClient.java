package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.DeleteEndpoint;
import com.lognex.api.clients.endpoints.PutEndpoint;
import com.lognex.api.entities.ProductFolderEntity;

public final class ProductFolderByIdClient
        extends ApiClient
        implements
        PutEndpoint<ProductFolderEntity>,
        DeleteEndpoint {

    public ProductFolderByIdClient(LognexApi api, String id) {
        super(api, "/entity/productfolder/" + id, ProductFolderEntity.class);
    }
}
