package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.entities.agents.EmployeeEntity;

public final class EmployeeClient
        extends ApiClient
        implements GetListEndpoint<EmployeeEntity> {

    public EmployeeClient(LognexApi api) {
        super(api, "/entity/employee/", EmployeeEntity.class);
    }
}
