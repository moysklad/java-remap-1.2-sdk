package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.entities.Attribute;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;

import java.io.IOException;

public interface ProductMetadataAttributeEndpoint extends MetadataAttributeEndpoint {

    @Override
    @ApiEndpoint
    default Attribute metadataAttributes(String id) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), "/entity/product/metadata/attributes/" + id).
                get(Attribute.class);
    }

    @Override
    @ApiEndpoint
    default ListEntity<Attribute> metadataAttributes() throws IOException, ApiClientException {
        ListEntity<Attribute> attributes = HttpRequestExecutor.
                path(api(), "/entity/product/metadata/attributes/").
                list(Attribute.class);
        return attributes;
    }

    @Override
    @ApiEndpoint
    default Attribute createMetadataAttribute(Attribute newEntity) throws IOException, ApiClientException {
        Attribute responseEntity = HttpRequestExecutor.
                path(api(), "/entity/product/metadata/attributes/").
                body(newEntity).
                post(Attribute.class);
        newEntity.set(responseEntity);
        return newEntity;
    }

    @Override
    @ApiEndpoint
    default Attribute updateMetadataAttribute(String id, Attribute newEntity) throws IOException, ApiClientException {
        Attribute responseEntity = HttpRequestExecutor.
                path(api(), "/entity/product/metadata/attributes/" + id).
                body(newEntity).
                put(Attribute.class);
        newEntity.set(responseEntity);
        return newEntity;
    }

    @Override
    @ApiEndpoint
    default Attribute updateMetadataAttribute(Attribute newEntity) throws IOException, ApiClientException {
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
    default void deleteMetadataAttribute(Attribute entity) throws IOException, ApiClientException {
        deleteMetadataAttribute(entity.getId());
    }
}
