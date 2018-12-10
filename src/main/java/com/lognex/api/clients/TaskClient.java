package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.TaskEntity;

public final class TaskClient
        extends ApiClient
        implements
        GetListEndpoint<TaskEntity>,
        PostEndpoint<TaskEntity>,
        DeleteByIdEndpoint,
        GetByIdEndpoint<TaskEntity>,
        PutByIdEndpoint<TaskEntity> {

    public TaskClient(LognexApi api) {
        super(api, "/entity/task/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return TaskEntity.class;
    }
}
