package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.entities.RetailShiftEntity;

public final class RetailShiftClient
        extends ApiClient
        implements
        GetListEndpoint<RetailShiftEntity>,
        DeleteByIdEndpoint {

    public RetailShiftClient(LognexApi api) {
        super(api, "/entity/retailshift/", RetailShiftEntity.class);
    }
}
