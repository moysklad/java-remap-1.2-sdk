package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.agents.Employee;
import com.lognex.api.responses.metadata.MetadataAttributeSharedResponse;

public final class EmployeeClient
        extends EntityClientBase
        implements
        GetListEndpoint<Employee>,
        PostEndpoint<Employee>,
        DeleteByIdEndpoint,
        MetadataEndpoint<MetadataAttributeSharedResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<Employee>,
        PutByIdEndpoint<Employee>,
        MassCreateUpdateDeleteEndpoint<Employee>,
        HasPermissionsEndpoint,
        HasAccessManagmentEndpoint {

    public EmployeeClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/employee/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Employee.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedResponse.class;
    }
}
