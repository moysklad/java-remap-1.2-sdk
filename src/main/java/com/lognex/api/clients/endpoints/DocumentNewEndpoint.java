package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.DocumentTemplateEntity;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.DocumentEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;
import com.lognex.api.utils.MetaHrefUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
        if (documentTemplateEntity.getDocument() != null) {
            documentTemplateEntity.setDocument(MetaHrefUtils.fillMeta(documentTemplateEntity.getDocument(), api().getHost()));
        } else if (documentTemplateEntity.getDocuments() != null) {
            List<DocumentEntity> documents = documentTemplateEntity.getDocuments();
            documents = documents.stream()
                    .map(d -> MetaHrefUtils.fillMeta(d, api().getHost()))
                    .collect(Collectors.toList());
            documentTemplateEntity.setDocuments(documents);
        }
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
