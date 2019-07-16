package com.lognex.api.clients.endpoints;

import com.lognex.api.clients.documents.DocumentMetadataClient;
import com.lognex.api.entities.MetaEntity;

public interface DocumentMetadataEndpoint<T extends MetaEntity> extends Endpoint {
    @ApiChainElement
    default DocumentMetadataClient<T> metadata() {
        return new DocumentMetadataClient<>(api(), path(), (Class<T>) metaEntityClass());
    }
}
