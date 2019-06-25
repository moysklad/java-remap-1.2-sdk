package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.ConsignmentEntity;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.metadata.MetadataAttributeResponse;

public final class ConsignmentClient
        extends ApiClient
        implements
        GetListEndpoint<ConsignmentEntity>,
        PostEndpoint<ConsignmentEntity>,
        DeleteByIdEndpoint,
        MetadataEndpoint<MetadataAttributeResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<ConsignmentEntity>,
        PutByIdEndpoint<ConsignmentEntity> {

    public ConsignmentClient(LognexApi api) {
        super(api, "/entity/consignment/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return ConsignmentEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeResponse.class;
    }
}
