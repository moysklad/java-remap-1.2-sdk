package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.Task;

public final class TaskClient
        extends EntityClientBase
        implements
        GetListEndpoint<Task>,
        GetByIdEndpoint<Task>,
        PostEndpoint<Task>,
        PutByIdEndpoint<Task>,
        DeleteByIdEndpoint {

    TaskClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/task/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Task.class;
    }
}
