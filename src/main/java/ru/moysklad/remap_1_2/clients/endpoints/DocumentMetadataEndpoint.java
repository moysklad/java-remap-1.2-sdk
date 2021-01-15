package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.clients.documents.DocumentMetadataClient;
import ru.moysklad.remap_1_2.entities.MetaEntity;

public interface DocumentMetadataEndpoint<T extends MetaEntity> extends Endpoint {
    @ApiChainElement
    default DocumentMetadataClient<T> metadata() {
        return new DocumentMetadataClient<>(api(), path(), (Class<T>) metaEntityClass());
    }
}
