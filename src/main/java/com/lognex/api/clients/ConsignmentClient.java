package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.ConsignmentEntity;

public final class ConsignmentClient
        extends ApiClient
        implements
        GetListEndpoint<ConsignmentEntity>,
        PostEndpoint<ConsignmentEntity> {

    public ConsignmentClient(LognexApi api) {
        super(api, "/entity/consignment", ConsignmentEntity.class);
    }
}
