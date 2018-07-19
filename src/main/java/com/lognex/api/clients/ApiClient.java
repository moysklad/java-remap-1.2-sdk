package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.Endpoint;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.MetadataListResponse;

public abstract class ApiClient implements Endpoint {
    private final LognexApi api;
    private final Class<? extends MetaEntity> entityClass;
    private final Class<? extends MetadataListResponse> metaDataEntityClass;
    private final String path;

    public ApiClient(LognexApi api, String path, Class<? extends MetaEntity> entityClass, Class<? extends MetadataListResponse> metaDataEntityClass) {
        this.api = api;
        this.entityClass = entityClass;
        this.metaDataEntityClass = metaDataEntityClass;
        this.path = path;
    }

    public ApiClient(LognexApi api, String path, Class<? extends MetaEntity> entityClass) {
        this(api, path, entityClass, null);
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
        return entityClass;
    }

    @Override
    public Class<? extends MetadataListResponse> metaEntityClass() {
        return metaDataEntityClass;
    }
}
