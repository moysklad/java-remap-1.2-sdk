package com.lognex.api.clients.endpoints;

import com.lognex.api.clients.documents.DocumentMetadataClientEntity;
import com.lognex.api.entities.MetaEntity;

public interface DocumentMetadataEndpoint<T extends MetaEntity> extends Endpoint {
    @ApiChainElement
    default DocumentMetadataClientEntity<T> metadata() {
        return new DocumentMetadataClientEntity<>(api(), path(), (Class<T>) metaEntityClass());
    }
}
