package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.products.ProductEntity;

public final class ProductClient
        extends ApiClient
        implements
        GetListEndpoint<ProductEntity>,
        PostEndpoint<ProductEntity>,
        DeleteByIdEndpoint {

    public ProductClient(LognexApi api) {
        super(api, "/entity/product/", ProductEntity.class);
    }
}
