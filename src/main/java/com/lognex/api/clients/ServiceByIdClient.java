package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.DeleteEndpoint;
import com.lognex.api.clients.endpoints.PutEndpoint;
import com.lognex.api.entities.ProductFolderEntity;

public final class ServiceByIdClient
        extends ApiClient
        implements
        PutEndpoint<ProductFolderEntity>,
        DeleteEndpoint {

    public ServiceByIdClient(LognexApi api, String id) {
        super(api, "/entity/service/" + id, ProductFolderEntity.class);
    }
}
