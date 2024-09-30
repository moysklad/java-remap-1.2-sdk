package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.Attribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.agents.Employee;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedResponse;

public final class EmployeeClient
        extends EntityClientBase
        implements
        GetListEndpoint<Employee>,
        PostEndpoint<Employee>,
        DeleteByIdEndpoint,
        MetadataEndpoint<MetadataAttributeSharedResponse<Attribute>>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<Employee>,
        PutByIdEndpoint<Employee>,
        MassCreateUpdateDeleteEndpoint<Employee>,
        HasPermissionsEndpoint,
        HasAccessManagmentEndpoint {

    public EmployeeClient(ru.moysklad.remap_1_2.ApiClient api) {
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
