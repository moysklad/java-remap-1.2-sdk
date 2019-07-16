package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.Endpoint;
import com.lognex.api.entities.MetaEntity;

public abstract class EntityApiClient implements Endpoint {
    protected final com.lognex.api.ApiClient api;
    protected final String path;

    public EntityApiClient(com.lognex.api.ApiClient api, String path) {
        this.api = api;
        this.path = path;
    }

    @Override
    public String path() {
        return path;
    }

    @Override
    public com.lognex.api.ApiClient api() {
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
