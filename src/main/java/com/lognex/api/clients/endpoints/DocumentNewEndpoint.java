package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.DocumentTemplate;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.DocumentEntity;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.MetaHrefUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.lognex.api.utils.Constants.API_PATH;

public interface DocumentNewEndpoint<T extends MetaEntity> extends Endpoint {
    @ApiEndpoint
    default T templateDocument() throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + "new").
                body(new Object()).
                put((Class<T>) entityClass());
    }

    @ApiEndpoint
    default T templateDocument(DocumentTemplate documentTemplate) throws IOException, ApiClientException {
        if (documentTemplate.getDocument() != null) {
            MetaHrefUtils.fillMeta(documentTemplate.getDocument(), api().getHost() + API_PATH);
        } else if (documentTemplate.getDocuments() != null) {
            List<DocumentEntity> documents = documentTemplate.getDocuments();
            documents = documents.stream()
                    .map(d -> MetaHrefUtils.fillMeta(d, api().getHost() + API_PATH))
                    .collect(Collectors.toList());
            documentTemplate.setDocuments(documents);
        }
        return HttpRequestExecutor.
                path(api(), path() + "new").
                body(documentTemplate).
                put((Class<T>) entityClass());
    }

    @ApiEndpoint
    default T templateDocument(String templateClassString, DocumentEntity document) throws IOException, ApiClientException {
        DocumentTemplate documentTemplate = new DocumentTemplate();
        documentTemplate.setDocumentType(templateClassString);
        documentTemplate.setDocument(document);

        return templateDocument(documentTemplate);
    }

    @ApiEndpoint
    default T templateDocument(String templateClassString, List<DocumentEntity> documents) throws IOException, ApiClientException {
        DocumentTemplate documentTemplate = new DocumentTemplate();
        documentTemplate.setDocumentType(templateClassString);
        documentTemplate.setDocuments(documents);

        return templateDocument(documentTemplate);
    }
}
