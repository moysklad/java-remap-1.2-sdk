package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.endpoints.Endpoint;
import ru.moysklad.remap_1_2.entities.MetaEntity;

public abstract class EntityClientBase implements Endpoint {
    protected final ApiClient api;
    protected final String path;

    public EntityClientBase(ApiClient api, String path) {
        this.api = api;
        this.path = path;
    }

    @Override
    public String path() {
        return path;
    }

    @Override
    public ApiClient api() {
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
