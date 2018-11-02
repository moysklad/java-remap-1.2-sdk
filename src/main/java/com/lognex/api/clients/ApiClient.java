package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.Endpoint;
import com.lognex.api.entities.MetaEntity;

public abstract class ApiClient implements Endpoint {
    protected final LognexApi api;
    protected final String path;

    public ApiClient(LognexApi api, String path) {
        this.api = api;
        this.path = path;
    }

    @Override
    public String path() {
        return path;
    }

    @Override
    public LognexApi api() {
        return api;
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return null;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return null;
    }

    @Override
    public Class<? extends MetaEntity> positionEntityClass() {
        return null;
    }
}
