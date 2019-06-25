package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.agents.EmployeeEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedResponse;

public final class EmployeeClient
        extends ApiClient
        implements
        GetListEndpoint<EmployeeEntity>,
        PostEndpoint<EmployeeEntity>,
        DeleteByIdEndpoint,
        MetadataEndpoint<MetadataAttributeSharedResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<EmployeeEntity>,
        PutByIdEndpoint<EmployeeEntity> {

    public EmployeeClient(LognexApi api) {
        super(api, "/entity/employee/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return EmployeeEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedResponse.class;
    }
}
