package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.DeleteEndpoint;
import com.lognex.api.clients.endpoints.PutEndpoint;
import com.lognex.api.entities.products.ProductEntity;

public final class ProductByIdClient
        extends ApiClient
        implements
        PutEndpoint<ProductEntity>,
        DeleteEndpoint {

    public ProductByIdClient(LognexApi api, String id) {
        super(api, "/entity/product/" + id, ProductEntity.class);
    }
}
