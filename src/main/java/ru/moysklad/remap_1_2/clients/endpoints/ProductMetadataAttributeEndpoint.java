package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.entities.AttributeCustomEntity;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;

import java.io.IOException;

public interface ProductMetadataAttributeEndpoint extends MetadataAttributeEndpoint {

    @Override
    @ApiEndpoint
    default AttributeCustomEntity metadataAttributes(String id) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), "/entity/product/metadata/attributes/" + id).
                get(AttributeCustomEntity.class);
    }

    @Override
    @ApiEndpoint
    default ListEntity<AttributeCustomEntity> metadataAttributes() throws IOException, ApiClientException {
        ListEntity<AttributeCustomEntity> attributes = HttpRequestExecutor.
                path(api(), "/entity/product/metadata/attributes/").
                list(AttributeCustomEntity.class);
        return attributes;
    }

    @Override
    @ApiEndpoint
    default AttributeCustomEntity createMetadataAttribute(AttributeCustomEntity newEntity) throws IOException, ApiClientException {
        AttributeCustomEntity responseEntity = HttpRequestExecutor.
                path(api(), "/entity/product/metadata/attributes/").
                body(newEntity).
                post(AttributeCustomEntity.class);
        newEntity.set(responseEntity);
        return newEntity;
    }

    @Override
    @ApiEndpoint
    default AttributeCustomEntity updateMetadataAttribute(String id, AttributeCustomEntity newEntity) throws IOException, ApiClientException {
        AttributeCustomEntity responseEntity = HttpRequestExecutor.
                path(api(), "/entity/product/metadata/attributes/" + id).
                body(newEntity).
                put(AttributeCustomEntity.class);
        newEntity.set(responseEntity);
        return newEntity;
    }

    @Override
    @ApiEndpoint
    default AttributeCustomEntity updateMetadataAttribute(AttributeCustomEntity newEntity) throws IOException, ApiClientException {
        return updateMetadataAttribute(newEntity.getId(), newEntity);
    }

    @Override
    @ApiEndpoint
    default void deleteMetadataAttribute(String id) throws IOException, ApiClientException {
        HttpRequestExecutor.
                path(api(), "/entity/product/metadata/attributes/" + id).
                delete();
    }

    @Override
    @ApiEndpoint
    default void deleteMetadataAttribute(AttributeCustomEntity entity) throws IOException, ApiClientException {
        deleteMetadataAttribute(entity.getId());
    }
}
