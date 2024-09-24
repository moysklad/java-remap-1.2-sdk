package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.entities.AttributeOperation;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;

import java.io.IOException;

public interface MetadataAttributeOperationEndpoint extends Endpoint {
    @ApiEndpoint
    default AttributeOperation metadataAttributes(String id) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + "metadata/attributes/" + id).
                get(AttributeOperation.class);
    }

    @ApiEndpoint
    default ListEntity<AttributeOperation> metadataAttributes() throws IOException, ApiClientException {
        ListEntity<AttributeOperation> attributes = HttpRequestExecutor.
                path(api(), path() + "metadata/attributes/").
                list(AttributeOperation.class);
        return attributes;
    }

    @ApiEndpoint
    default AttributeOperation createMetadataAttribute(AttributeOperation newEntity) throws IOException, ApiClientException {
        AttributeOperation responseEntity = HttpRequestExecutor.
                path(api(), path() + "metadata/attributes/").
                body(newEntity).
                post(AttributeOperation.class);
        newEntity.set(responseEntity);
        return newEntity;
    }

    @ApiEndpoint
    default AttributeOperation updateMetadataAttribute(String id, AttributeOperation newEntity) throws IOException, ApiClientException {
        AttributeOperation responseEntity = HttpRequestExecutor.
                path(api(), path() + "metadata/attributes/" + id).
                body(newEntity).
                put(AttributeOperation.class);
        newEntity.set(responseEntity);
        return newEntity;
    }

    @ApiEndpoint
    default AttributeOperation updateMetadataAttribute(AttributeOperation newEntity) throws IOException, ApiClientException {
        return updateMetadataAttribute(newEntity.getId(), newEntity);
    }

    @ApiEndpoint
    default void deleteMetadataAttribute(String id) throws IOException, ApiClientException {
        HttpRequestExecutor.
                path(api(), path() + "metadata/attributes/" + id).
                delete();
    }

    @ApiEndpoint
    default void deleteMetadataAttribute(AttributeOperation entity) throws IOException, ApiClientException {
        deleteMetadataAttribute(entity.getId());
    }
}
