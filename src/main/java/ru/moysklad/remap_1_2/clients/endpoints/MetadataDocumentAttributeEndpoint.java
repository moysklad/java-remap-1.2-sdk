package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.entities.Attribute;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;

import java.io.IOException;

public interface MetadataDocumentAttributeEndpoint extends MetadataAttributeEndpoint {
    @ApiEndpoint
    default DocumentAttribute metadataAttributes(String id) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + "metadata/attributes/" + id).
                get(DocumentAttribute.class);
    }

    @ApiEndpoint
    default ListEntity<DocumentAttribute> metadataDocumentAttributes() throws IOException, ApiClientException {
        ListEntity<DocumentAttribute> documentAttribute = HttpRequestExecutor.
                path(api(), path() + "metadata/attributes/").
                list(ru.moysklad.remap_1_2.entities.DocumentAttribute.class);
        return documentAttribute;
    }

    @ApiEndpoint
    default DocumentAttribute createMetadataAttribute(DocumentAttribute newEntity) throws IOException, ApiClientException {
        DocumentAttribute responseEntity = HttpRequestExecutor.
                path(api(), path() + "metadata/attributes/").
                body(newEntity).
                post(DocumentAttribute.class);
        newEntity.set(responseEntity);
        return newEntity;
    }

    @ApiEndpoint
    default DocumentAttribute updateMetadataAttribute(String id, DocumentAttribute newEntity) throws IOException, ApiClientException {
        DocumentAttribute responseEntity = HttpRequestExecutor.
                path(api(), path() + "metadata/attributes/" + id).
                body(newEntity).
                put(DocumentAttribute.class);
        newEntity.set(responseEntity);
        return newEntity;
    }

    @ApiEndpoint
    default DocumentAttribute updateMetadataAttribute(DocumentAttribute newEntity) throws IOException, ApiClientException {
        return updateMetadataAttribute(newEntity.getId(), newEntity);
    }

    @ApiEndpoint
    default void deleteMetadataAttribute(DocumentAttribute entity) throws IOException, ApiClientException {
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
    default void deleteMetadataAttribute(Attribute entity) throws IOException, ApiClientException {
        MetadataAttributeEndpoint.super.deleteMetadataAttribute(entity);
    }
}
