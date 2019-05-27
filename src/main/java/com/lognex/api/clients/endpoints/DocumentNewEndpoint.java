package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.DocumentTemplateEntity;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.DocumentEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;
import java.util.List;

public interface DocumentNewEndpoint<T extends MetaEntity> extends Endpoint {
    @ApiEndpoint
    default T newDocument() throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + "new").
                body(new Object()).
                put((Class<T>) entityClass());
    }

    @ApiEndpoint
    default T newDocument(DocumentTemplateEntity documentTemplateEntity) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + "new").
                body(documentTemplateEntity).
                put((Class<T>) entityClass());
    }

    @ApiEndpoint
    default T newDocument(String templateClassString, DocumentEntity document) throws IOException, LognexApiException {
        DocumentTemplateEntity documentTemplateEntity = new DocumentTemplateEntity();
        documentTemplateEntity.setDocumentType(templateClassString);
        documentTemplateEntity.setDocument(document);

        return newDocument(documentTemplateEntity);
    }

    @ApiEndpoint
    default T newDocument(String templateClassString, List<DocumentEntity> documents) throws IOException, LognexApiException {
        DocumentTemplateEntity documentTemplateEntity = new DocumentTemplateEntity();
        documentTemplateEntity.setDocumentType(templateClassString);
        documentTemplateEntity.setDocuments(documents);

        return newDocument(documentTemplateEntity);
    }
}
