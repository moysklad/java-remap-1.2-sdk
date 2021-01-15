package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.entities.MetaEntity;

public interface Endpoint {
    String path();

    ApiClient api();

    Class<? extends MetaEntity> entityClass();

    Class<? extends MetaEntity> metaEntityClass();

    Class<? extends MetaEntity> positionEntityClass();
}
