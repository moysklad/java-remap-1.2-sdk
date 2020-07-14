package com.lognex.api.clients;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.Consignment;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.metadata.MetadataAttributeResponse;

public final class ConsignmentClient
        extends EntityClientBase
        implements
        GetListEndpoint<Consignment>,
        PostEndpoint<Consignment>,
        DeleteByIdEndpoint,
        MetadataEndpoint<MetadataAttributeResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<Consignment>,
        PutByIdEndpoint<Consignment>,
        MassCreateUpdateDeleteEndpoint<Consignment> {

    public ConsignmentClient(ApiClient api) {
        super(api, "/entity/consignment/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Consignment.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeResponse.class;
    }
}
