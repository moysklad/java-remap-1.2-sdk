package com.lognex.api.clients.endpoints;

import com.lognex.api.LognexApi;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.MetadataListResponse;

public interface Endpoint {
    String path();

    LognexApi api();

    Class<? extends MetaEntity> entityClass();

    Class<? extends MetadataListResponse> metaEntityClass();
}
