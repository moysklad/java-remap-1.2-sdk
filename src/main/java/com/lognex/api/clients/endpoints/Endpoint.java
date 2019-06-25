package com.lognex.api.clients.endpoints;

import com.lognex.api.LognexApi;
import com.lognex.api.entities.MetaEntity;

public interface Endpoint {
    String path();

    LognexApi api();

    Class<? extends MetaEntity> entityClass();

    Class<? extends MetaEntity> metaEntityClass();

    Class<? extends MetaEntity> positionEntityClass();
}
