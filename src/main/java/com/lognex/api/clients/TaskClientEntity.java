package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.Task;

public final class TaskClientEntity
        extends EntityApiClient
        implements
        GetListEndpoint<Task>,
        GetByIdEndpoint<Task>,
        PostEndpoint<Task>,
        PutByIdEndpoint<Task>,
        DeleteByIdEndpoint {

    TaskClientEntity(com.lognex.api.ApiClient api) {
        super(api, "/entity/task/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Task.class;
    }
}
