package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.clients.endpoints.PutEndpoint;
import com.lognex.api.entities.products.ServiceEntity;

public final class ServiceClient
        extends ApiClient
        implements
        GetListEndpoint<ServiceEntity>,
        PostEndpoint<ServiceEntity>,
        PutEndpoint<ServiceEntity>,
        DeleteByIdEndpoint {

    public ServiceClient(LognexApi api) {
        super(api, "/entity/service/", ServiceEntity.class);
    }
}
