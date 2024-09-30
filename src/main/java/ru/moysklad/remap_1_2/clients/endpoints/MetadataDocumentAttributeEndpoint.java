package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.entities.Attribute;
import ru.moysklad.remap_1_2.entities.OperationAttribute;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;

import java.io.IOException;

public interface MetadataDocumentAttributeEndpoint extends MetadataAttributeEndpoint {
    @ApiEndpoint
    default OperationAttribute metadataAttributes(String id) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + "metadata/attributes/" + id).
                get(OperationAttribute.class);
    }

    @ApiEndpoint
    default ListEntity<OperationAttribute> metadataDocumentAttributes() throws IOException, ApiClientException {
        ListEntity<OperationAttribute> DocumentAttribute = HttpRequestExecutor.
                path(api(), path() + "metadata/attributes/").
                list(OperationAttribute.class);
        return DocumentAttribute;
    }

    @ApiEndpoint
    default OperationAttribute createMetadataAttribute(OperationAttribute newEntity) throws IOException, ApiClientException {
        OperationAttribute responseEntity = HttpRequestExecutor.
                path(api(), path() + "metadata/attributes/").
                body(newEntity).
                post(OperationAttribute.class);
        newEntity.set(responseEntity);
        return newEntity;
    }

    @ApiEndpoint
    default OperationAttribute updateMetadataAttribute(String id, OperationAttribute newEntity) throws IOException, ApiClientException {
        OperationAttribute responseEntity = HttpRequestExecutor.
                path(api(), path() + "metadata/attributes/" + id).
                body(newEntity).
                put(OperationAttribute.class);
        newEntity.set(responseEntity);
        return newEntity;
    }

    @ApiEndpoint
    default OperationAttribute updateMetadataAttribute(OperationAttribute newEntity) throws IOException, ApiClientException {
        return updateMetadataAttribute(newEntity.getId(), newEntity);
    }

    @ApiEndpoint
    default void deleteMetadataAttribute(OperationAttribute entity) throws IOException, ApiClientException {
        deleteMetadataAttribute(entity.getId());
    }

    @ApiEndpoint
    @Deprecated
    default ListEntity<Attribute> metadataAttributes() throws IOException, ApiClientException {
        return MetadataAttributeEndpoint.super.metadataAttributes();
    }

    @Override
    @Deprecated
    default Attribute createMetadataAttribute(Attribute newEntity) throws IOException, ApiClientException {
        return MetadataAttributeEndpoint.super.createMetadataAttribute(newEntity);
    }

    @Override
    @Deprecated
    default Attribute updateMetadataAttribute(String id, Attribute newEntity) throws IOException, ApiClientException {
        return MetadataAttributeEndpoint.super.updateMetadataAttribute(id, newEntity);
    }

    @Override
    @Deprecated
    default Attribute updateMetadataAttribute(Attribute newEntity) throws IOException, ApiClientException {
        return MetadataAttributeEndpoint.super.updateMetadataAttribute(newEntity);
    }

    @Override
    @Deprecated
    default void deleteMetadataAttribute(String id) throws IOException, ApiClientException {
        MetadataAttributeEndpoint.super.deleteMetadataAttribute(id);
    }

    @Override
    @Deprecated
    default void deleteMetadataAttribute(Attribute entity) throws IOException, ApiClientException {
        MetadataAttributeEndpoint.super.deleteMetadataAttribute(entity);
    }
}
