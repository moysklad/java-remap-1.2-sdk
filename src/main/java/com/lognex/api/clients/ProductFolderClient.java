package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.clients.endpoints.PutEndpoint;
import com.lognex.api.entities.ProductFolderEntity;

public final class ProductFolderClient
        extends ApiClient
        implements
        GetListEndpoint<ProductFolderEntity>,
        PostEndpoint<ProductFolderEntity>,
        PutEndpoint<ProductFolderEntity>,
        DeleteByIdEndpoint {

    public ProductFolderClient(LognexApi api) {
        super(api, "/entity/productfolder/", ProductFolderEntity.class);
    }
}
